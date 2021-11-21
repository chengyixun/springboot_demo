package com.example.mybatispluscrud.common;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ExcelListener
 * @Author: amy
 * @Description: ExcelListener
 * @Date: 2021/11/9
 * @Version: 1.0
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener {

    private List<Object> datas = new ArrayList<>();

    @Override
    public void invoke(Object data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data ));
        datas.add(data);

    }

    /**
     * 所有的数据解析玩 完成，都会调用
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    public List<Object> getDatas(){
        return datas;
    }

    public void setDatas(List<Object> datas){
        this.datas = datas;
    }
}
