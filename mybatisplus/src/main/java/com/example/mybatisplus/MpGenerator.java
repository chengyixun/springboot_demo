package com.example.mybatisplus;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-08 21:19
 * @Description:
 * @Modified By:
 */
public class MpGenerator {

	/*
	 * public static void main(String[] args) { GlobalConfig config = new
	 * GlobalConfig(); DataSourceConfig dataSourceConfig = new DataSourceConfig();
	 * dataSourceConfig .setDbType(DbType.MYSQL) .setUrl(
	 * "jdbc:mysql://127.0.0.1:3306/mp?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC")
	 * .setUsername("root") .setPassword("root")
	 * .setDriverName("com.mysql.cj.jdbc.Driver"); StrategyConfig strategyConfig =
	 * new StrategyConfig(); strategyConfig .setCapitalMode(true)
	 * .setEntityLombokModel(true) .setNaming(NamingStrategy.underline_to_camel);
	 * 
	 * //这里因为我是多模块项目，所以需要加上子模块的名称，以便直接生成到该目录下，如果是单模块项目，可以将后面的去掉 String projectPath=
	 * System.getProperty("user.dir")+"/mybatisplus"; //自定义配置 InjectionConfig cfg =
	 * new InjectionConfig() {
	 * 
	 * @Override public void initMap() {
	 * 
	 * } }; // 如果模板引擎是 freemarker String templatePath = "/templates/mapper.xml.ftl";
	 * // 如果模板引擎是 velocity // String templatePath = "/templates/mapper.xml.vm";
	 * 
	 * // 自定义输出配置 List<FileOutConfig> focList = new ArrayList<>(); // 自定义配置会被优先输出
	 * focList.add(new FileOutConfig(templatePath) {
	 * 
	 * @Override public String outputFile(TableInfo tableInfo) { // 自定义输出文件名 return
	 * projectPath + "/src/main/resources/mapper/" + "user" + "/" +
	 * tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML; } });
	 * 
	 * cfg.setFileOutConfigList(focList);
	 * 
	 * config.setActiveRecord(false) .setEnableCache(false) .setAuthor("wangyu")
	 * .setOutputDir(projectPath+"/src/main/java") .setFileOverride(true)
	 * .setServiceName("%sService"); new AutoGenerator() .setGlobalConfig(config)
	 * .setDataSource(dataSourceConfig) .setStrategy(strategyConfig)
	 * .setTemplateEngine(new FreemarkerTemplateEngine()) .setCfg(cfg) //这里进行包名的设置
	 * .setPackageInfo( new PackageConfig() .setParent("com.example.mybatisplus")
	 * .setController("controller") .setEntity("entity") .setMapper("mapper")
	 * .setServiceImpl("service.impl") .setService("service") ).execute();
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

}
