package com.example.tk.common.util;

import com.example.tk.common.annotation.ExcelColumn;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: ExcelUtil
 * @Author: amy
 * @Description: ExcelUtil
 * @Date: 2021/8/15
 * @Version: 1.0
 */
public class ExcelUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

	private ExcelUtil() {
	}

	private static final String EXCEL2003 = "xls";
	private static final String EXCEL2007 = "xlsx";
	private static final String DEFAULT_SHEET = "Sheet1";
	private static final String ERROR_RESULT = "ERROR";

	/**
	 * read excel to entity data list
	 *
	 * @param cls  class type
	 * @param file multipart file
	 * @param <T>  object
	 * @return object list
	 */
	public static <T> List<T> readExcel(Class<T> cls, MultipartFile file) {
		if (null == file) {
			LOGGER.error("target file dose not exist");
			return new ArrayList<>();
		}
		String fileName = file.getOriginalFilename();
		if (StringUtils.isBlank(fileName)) {
			LOGGER.error("target file name dose not exist");
			return new ArrayList<>();
		}
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			LOGGER.error("The file is not a xls file or xlsx file");
			return new ArrayList<>();
		}
		List<T> dataList = new ArrayList<>();
		Workbook workbook = null;
		try (InputStream is = file.getInputStream()) {
			if (fileName.endsWith(EXCEL2007)) {
				workbook = new XSSFWorkbook(is);
			}
			if (fileName.endsWith(EXCEL2003)) {
				workbook = new HSSFWorkbook(is);
			}
			if (workbook != null) {
				// ????????? ?????? value-->bean columns
				Map<String, List<Field>> classMap = new HashMap<>();
				List<Field> fields = Stream.of(cls.getDeclaredFields()).collect(Collectors.toList());
				fields.forEach(field -> {
					ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
					if (annotation != null) {
						String value = annotation.value();
						if (StringUtils.isBlank(value)) {
							return; // return??????????????????continue???????????? ??????
						}
						if (!classMap.containsKey(value)) {
							classMap.put(value, new ArrayList<>());
						}
						field.setAccessible(true);
						classMap.get(value).add(field);
					}
				});
				// ??????-->columns
				Map<Integer, List<Field>> reflectionMap = new HashMap<>(16);
				// ?????????????????????sheet
				Sheet sheet = workbook.getSheetAt(0);

				boolean firstRow = true;
				for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					// ?????? ????????????
					if (firstRow) {
						for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
							Cell cell = row.getCell(j);
							String cellValue = getCellValue(cell);
							if (classMap.containsKey(cellValue)) {
								reflectionMap.put(j, classMap.get(cellValue));
							}
						}
						firstRow = false;
					} else {
						// ???????????????
						if (row == null) {
							continue;
						}
						T t = cls.getDeclaredConstructor().newInstance();
						// ????????????????????????
						boolean allBlank = true;
						for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
							if (reflectionMap.containsKey(j)) {
								Cell cell = row.getCell(j);
								String cellValue = getCellValue(cell);
								if (StringUtils.isNotBlank(cellValue)) {
									allBlank = false;
								}
								List<Field> fieldList = reflectionMap.get(j);
								fieldList.forEach(x -> {
									x.setAccessible(true);
									try {
										handleField(t, cellValue, x);
									} catch (Exception e) {
										LOGGER.error("reflect field:{} value:{} exception!", x.getName(), cellValue, e);
									}
								});
							}
						}
						if (!allBlank) {
							dataList.add(t);
						} else {
							LOGGER.warn("row:{} is blank ignore!", i);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("excel parse error,wps excel must be .xlsx file", e);
			return new ArrayList<>();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (Exception e) {
					LOGGER.error("parse excel exception!", e);
				}
			}
		}
		return dataList;
	}

	/**
	 * handle file set value
	 *
	 * @param t     object
	 * @param value value
	 * @param field object field
	 * @param <T>   object type
	 */
	private static <T> void handleField(T t, String value, Field field)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
		Class<?> type = field.getType();
		if (type == void.class || StringUtils.isBlank(value)) {
			return;
		}
		if (type == Object.class) {
			field.set(t, value);
			// ????????????
		} else if (type.getSuperclass() == null || type.getSuperclass() == Number.class) {
			if (type == int.class || type == Integer.class) {
				field.set(t, NumberUtils.toInt(value));
			} else if (type == long.class || type == Long.class) {
				field.set(t, NumberUtils.toLong(value));
			} else if (type == byte.class || type == Byte.class) {
				field.set(t, NumberUtils.toByte(value));
			} else if (type == short.class || type == Short.class) {
				field.set(t, NumberUtils.toShort(value));
			} else if (type == double.class || type == Double.class) {
				field.set(t, NumberUtils.toDouble(value));
			} else if (type == float.class || type == Float.class) {
				field.set(t, NumberUtils.toFloat(value));
			} else if (type == char.class || type == Character.class) {
				field.set(t, CharUtils.toChar(value));
			} else if (type == boolean.class) {
				field.set(t, BooleanUtils.toBoolean(value));
			} else if (type == BigDecimal.class) {
				field.set(t, new BigDecimal(value));
			}
		} else if (type == Boolean.class) {
			field.set(t, BooleanUtils.toBoolean(value));
		} else if (type == Date.class) {
			//
			field.set(t, value);
		} else if (type == String.class) {
			field.set(t, value);
		} else {
			Constructor<?> constructor = type.getConstructor(String.class);
			field.set(t, constructor.newInstance(value));
		}
	}

	/**
	 * get cell value
	 *
	 * @param cell excel cell
	 * @return value
	 */
	private static String getCellValue(Cell cell) {
		if (cell == null) {
			return StringUtils.EMPTY;
		}
		switch (cell.getCellTypeEnum()) {
		case STRING:
			return StringUtils.trimToEmpty(cell.getStringCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case NUMERIC:
			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
				return DateUtil.getJavaDate(cell.getNumericCellValue()).toString();
			} else {
				return BigDecimal.valueOf(cell.getNumericCellValue()).toString();
			}
		case FORMULA:
			return StringUtils.trimToEmpty(cell.getCellFormula());
		case BLANK:
			return StringUtils.EMPTY;
		case ERROR:
			return ERROR_RESULT;
		default:
			return cell.toString().trim();
		}
		/*
		 * if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) { if
		 * (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) { return
		 * DateUtil.getJavaDate(cell.getNumericCellValue()).toString(); } else { return
		 * BigDecimal.valueOf(cell.getNumericCellValue()).toString(); } } else if
		 * (cell.getCellType() == Cell.CELL_TYPE_STRING) { return
		 * StringUtils.trimToEmpty(cell.getStringCellValue()); } else if
		 * (cell.getCellType() == Cell.CELL_TYPE_FORMULA) { return
		 * StringUtils.trimToEmpty(cell.getCellFormula()); } else if (cell.getCellType()
		 * == Cell.CELL_TYPE_BLANK) { return StringUtils.EMPTY; } else if
		 * (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) { return
		 * String.valueOf(cell.getBooleanCellValue()); } else if (cell.getCellType() ==
		 * Cell.CELL_TYPE_ERROR) { return ERROR_RESULT; } else { return
		 * cell.toString().trim(); }
		 */
	}

	/**
	 * write excel split header with data list
	 *
	 * @param response HttpServletResponse
	 * @param dataList data : key->row index,value->row data
	 * @param headers  header list
	 * @param fileName file name
	 */
	public static void writeExcel(@NotNull HttpServletResponse response, @NotNull Map<String, List<String>> dataList,
			@NotNull List<String> headers, @NotNull String fileName) {
		Workbook wb = getWorkbookWithHeaders(dataList, headers);
		// ???????????????excel
		buildExcelDocument(fileName, wb, response);
	}

	/**
	 * get workbook by data list and headers
	 *
	 * @param dataList data list
	 * @param headers  header list
	 * @return excel workbook
	 */
	@NotNull
	private static Workbook getWorkbookWithHeaders(Map<String, List<String>> dataList, List<String> headers) {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet(DEFAULT_SHEET);
		AtomicInteger ai = new AtomicInteger();
		Row row = sheet.createRow(ai.getAndIncrement());
		AtomicInteger aj = new AtomicInteger();
		if (!CollectionUtils.isEmpty(headers)) {
			// ????????????
			headers.forEach(columnName -> {
				Cell cell = row.createCell(aj.getAndIncrement());
				CellStyle cellStyle = wb.createCellStyle();
				cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyle.setAlignment(HorizontalAlignment.CENTER);

				Font font = wb.createFont();
				font.setBold(false);
				cellStyle.setFont(font);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(columnName);
			});
		}
		// ????????????
		if (!CollectionUtils.isEmpty(dataList)) {
			for (Map.Entry<String, List<String>> entry : dataList.entrySet()) {
				// write each row data
				Row row1 = sheet.createRow(ai.getAndIncrement());
				AtomicInteger atomicInteger = new AtomicInteger();
				List<String> values = entry.getValue();
				for (String value : values) {
					Cell cell = row1.createCell(atomicInteger.getAndIncrement());
					if (value != null) {
						cell.setCellValue(value);
					}
				}
			}
		}
		// freezing current pane
		wb.getSheet(DEFAULT_SHEET).createFreezePane(0, 1, 0, 1);
		return wb;
	}

	/**
	 * download excel with browser
	 *
	 * @param fileName file name
	 * @param wb       workshop
	 * @param response http response
	 */
	private static void buildExcelDocument(@NotNull String fileName, @NotNull Workbook wb,
			@NotNull HttpServletResponse response) {
		try {
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
			response.flushBuffer();
			wb.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
