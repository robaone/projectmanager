/*
 * Created on Apr 9, 2010
 *
 */
package com.robaone.jdo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;



import com.robaone.util.INIFileReader;
import com.robaone.util.Strmanip;

/**
 * @author arobateau  http://www.robaone.com
 *
 */
public class RO_JDO_Generator {

	private Connection m_con; 
	private Vector<String> m_fields = new Vector<String>();
	private String m_destination;
	private String m_package;
	private String m_table;
	private String m_identity;
	private String m_dbtype;
	private String m_idmanager;
	private String m_identity_class;
	private boolean m_jsonsupport = false;
	private String m_gwtpackage;
	private String m_gwtdestination;
	protected java.util.HashMap<String,String> m_mnemonics = new HashMap<String,String>();
	private String m_idmanager_class = null;
	public static void main(String[] args) {
		try{
			RO_JDO_Generator generator = new RO_JDO_Generator();
			generator.processArguments(args);
			generator.getConnection().close();
		}catch(Exception e){
			System.err.println("Error: "+e.getMessage());
			RO_JDO_Generator.showUsage();
		}finally{

		}
	}

	/**
	 * @throws SQLException
	 * @throws IOException
	 * 
	 */
	private void createClasses() throws SQLException, IOException {
		//Get the meta data.
		String sql_select = "select ";
		if(this.m_fields.size() > 0){
			for(int i = 0; i < this.m_fields.size();i++){
				if(i > 0){
					sql_select += ",";
				}
				sql_select += this.m_fields.get(i);
			}
		}else{
			sql_select += "* ";
		}
		sql_select += " from "+this.getSafeTable() + " where "+ this.getIdentityField() +" is null";
		PreparedStatement ps = this.getConnection().prepareStatement(sql_select);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData mdata = rs.getMetaData();
		//Generate the jdo class
		String jdo = this.generateJDO(mdata);
		//Generate the jdo manager class
		String jdomanager = this.generateJDOManager(mdata);
		//Generate the gwt helper class
		if(this.getGWTPackage() != null){
			String gwtdo = this.generateGWTDO(mdata);
			//Save the classes to the destination folder
			File destination = new File(this.getGWTDestination());
			destination.mkdirs();
			String classname = this.getJdoClassName()+"JSO";
			FileWriter fw = new FileWriter(destination+System.getProperty("file.separator")+classname+".java");
			System.out.println("Writing "+destination+System.getProperty("file.separator")+classname+".java");
			fw.write(gwtdo);
			fw.close();
		}
		rs.close();
		//Save the classes to the destination folder
		File destination = new File(this.getDestination());
		destination.mkdirs();
		String classname = this.getJdoClassName();
		FileWriter fw = new FileWriter(destination+System.getProperty("file.separator")+classname+"_jdo.java");
		System.out.println("Writing "+destination+System.getProperty("file.separator")+classname+"_jdo.java");
		fw.write(jdo);
		fw.close();
		fw = new FileWriter(destination+System.getProperty("file.separator")+classname+"_jdoManager.java");
		System.out.println("Writing "+destination+System.getProperty("file.separator")+classname+"_jdoManager.java");
		fw.write(jdomanager);
		fw.close();
	}

	private String getSafeTable() {
		HashMap<String,String> keywords = new HashMap<String,String>();
		if(this.m_dbtype.equalsIgnoreCase("mysql")){

		}else if(this.m_dbtype.equalsIgnoreCase("oracle")){

		}else if(this.m_dbtype.equalsIgnoreCase("sqlserver")){
			keywords.put("add", "[add]");
			keywords.put("all", "[all]");
			keywords.put("alter", "[alter]");
			keywords.put("and", "[and]");
			keywords.put("any", "[any]");
			keywords.put("as", "[as]");
			keywords.put("asc", "[asc]");
			keywords.put("authorization", "[authorization]");
			keywords.put("backup", "[backup]");
			keywords.put("begin", "[begin]");
			keywords.put("between", "[between]");
			keywords.put("break", "[break]");
			keywords.put("browse", "[browse]");
			keywords.put("bulk", "[bulk]");
			keywords.put("by", "[by]");
			keywords.put("cascade", "[cascade]");
			keywords.put("case", "[case]");
			keywords.put("check", "[check]");
			keywords.put("checkpoint", "[checkpoint]");
			keywords.put("close", "[close]");
			keywords.put("clustered", "[clustered]");
			keywords.put("coalesce", "[coalesce]");
			keywords.put("collate", "[collate]");
			keywords.put("column", "[column]");
			keywords.put("commit", "[commit]");
			keywords.put("compute", "[compute]");
			keywords.put("constraint", "[constraint]");
			keywords.put("contains", "[contains]");
			keywords.put("containstable", "[containstable]");
			keywords.put("continue", "[continue]");
			keywords.put("convert", "[convert]");
			keywords.put("create", "[create]");
			keywords.put("cross", "[cross]");
			keywords.put("current", "[current]");
			keywords.put("current_date", "[current_date]");
			keywords.put("current_time", "[current_time]");
			keywords.put("current_timestamp", "[current_timestamp]");
			keywords.put("current_user", "[current_user]");
			keywords.put("cursor", "[cursor]");
			keywords.put("database", "[database]");
			keywords.put("dbcc", "[dbcc]");
			keywords.put("deallocate", "[deallocate]");
			keywords.put("declare", "[declare]");
			keywords.put("default", "[default]");
			keywords.put("delete", "[delete]");
			keywords.put("deny", "[deny]");
			keywords.put("desc", "[desc]");
			keywords.put("disk", "[disk]");
			keywords.put("distinct", "[distinct]");
			keywords.put("distributed", "[distributed]");
			keywords.put("double", "[double]");
			keywords.put("drop", "[drop]");
			keywords.put("dummy", "[dummy]");
			keywords.put("dump", "[dump]");
			keywords.put("else", "[else]");
			keywords.put("end", "[end]");
			keywords.put("errlvl", "[errlvl]");
			keywords.put("escape", "[escape]");
			keywords.put("except", "[except]");
			keywords.put("exec", "[exec]");
			keywords.put("execute", "[execute]");
			keywords.put("exists", "[exists]");
			keywords.put("exit", "[exit]");
			keywords.put("fetch", "[fetch]");
			keywords.put("file", "[file]");
			keywords.put("fillfactor", "[fillfactor]");
			keywords.put("for", "[for]");
			keywords.put("foreign", "[foreign]");
			keywords.put("freetext", "[freetext]");
			keywords.put("freetexttable", "[freetexttable]");
			keywords.put("from", "[from]");
			keywords.put("full", "[full]");
			keywords.put("function", "[function]");
			keywords.put("goto", "[goto]");
			keywords.put("grant", "[grant]");
			keywords.put("group", "[group]");
			keywords.put("having", "[having]");
			keywords.put("holdlock", "[holdlock]");
			keywords.put("identity", "[identity]");
			keywords.put("identity_insert", "[identity_insert]");
			keywords.put("identitycol", "[identitycol]");
			keywords.put("if", "[if]");
			keywords.put("in", "[in]");
			keywords.put("index", "[index]");
			keywords.put("inner", "[inner]");
			keywords.put("insert", "[insert]");
			keywords.put("intersect", "[intersect]");
			keywords.put("into", "[into]");
			keywords.put("is", "[is]");
			keywords.put("join", "[join]");
			keywords.put("key", "[key]");
			keywords.put("kill", "[kill]");
			keywords.put("left", "[left]");
			keywords.put("like", "[like]");
			keywords.put("lineno", "[lineno]");
			keywords.put("load", "[load]");
			keywords.put("national", "[national]");
			keywords.put("nocheck", "[nocheck]");
			keywords.put("nonclustered", "[nonclustered]");
			keywords.put("not", "[not]");
			keywords.put("null", "[null]");
			keywords.put("nullif", "[nullif]");
			keywords.put("of", "[of]");
			keywords.put("off", "[off]");
			keywords.put("offsets", "[offsets]");
			keywords.put("on", "[on]");
			keywords.put("open", "[open]");
			keywords.put("opendatasource", "[opendatasource]");
			keywords.put("openquery", "[openquery]");
			keywords.put("openrowset", "[openrowset]");
			keywords.put("openxml", "[openxml]");
			keywords.put("option", "[option]");
			keywords.put("or", "[or]");
			keywords.put("order", "[order]");
			keywords.put("outer", "[outer]");
			keywords.put("over", "[over]");
			keywords.put("percent", "[percent]");
			keywords.put("plan", "[plan]");
			keywords.put("precision", "[precision]");
			keywords.put("primary", "[primary]");
			keywords.put("print", "[print]");
			keywords.put("proc", "[proc]");
			keywords.put("procedure", "[procedure]");
			keywords.put("public", "[public]");
			keywords.put("raiserror", "[raiserror]");
			keywords.put("read", "[read]");
			keywords.put("readtext", "[readtext]");
			keywords.put("reconfigure", "[reconfigure]");
			keywords.put("references", "[references]");
			keywords.put("replication", "[replication]");
			keywords.put("restore", "[restore]");
			keywords.put("restrict", "[restrict]");
			keywords.put("return", "[return]");
			keywords.put("revoke", "[revoke]");
			keywords.put("right", "[right]");
			keywords.put("rollback", "[rollback]");
			keywords.put("rowcount", "[rowcount]");
			keywords.put("rowguidcol", "[rowguidcol]");
			keywords.put("rule", "[rule]");
			keywords.put("save", "[save]");
			keywords.put("schema", "[schema]");
			keywords.put("select", "[select]");
			keywords.put("session_user", "[session_user]");
			keywords.put("set", "[set]");
			keywords.put("setuser", "[setuser]");
			keywords.put("shutdown", "[shutdown]");
			keywords.put("some", "[some]");
			keywords.put("statistics", "[statistics]");
			keywords.put("system_user", "[system_user]");
			keywords.put("table", "[table]");
			keywords.put("textsize", "[textsize]");
			keywords.put("then", "[then]");
			keywords.put("to", "[to]");
			keywords.put("top", "[top]");
			keywords.put("tran", "[tran]");
			keywords.put("transaction", "[transaction]");
			keywords.put("trigger", "[trigger]");
			keywords.put("truncate", "[truncate]");
			keywords.put("tsequal", "[tsequal]");
			keywords.put("union", "[union]");
			keywords.put("unique", "[unique]");
			keywords.put("update", "[update]");
			keywords.put("updatetext", "[updatetext]");
			keywords.put("use", "[use]");
			keywords.put("user", "[user]");
			keywords.put("values", "[values]");
			keywords.put("varying", "[varying]");
			keywords.put("view", "[view]");
			keywords.put("waitfor", "[waitfor]");
			keywords.put("when", "[when]");
			keywords.put("where", "[where]");
			keywords.put("while", "[while]");
			keywords.put("with", "[with]");
			keywords.put("writetext", "[writetext]");
		}
		if(keywords.get(this.getTable().toLowerCase()) != null){
			return keywords.get(this.getTable().toLowerCase());
		}else{
			return getTable();
		}
	}

	private String getGWTDestination() {
		return this.m_gwtdestination;
	}

	private String generateGWTDO(ResultSetMetaData mdata) throws SQLException {
		String classname = this.getTable().toUpperCase().charAt(0)+
				this.getTable().toLowerCase().substring(1)+"JSO";
		String str = "package "+this.getGWTPackage() + ";\n\n";
		str += "import com.google.gwt.core.client.JavaScriptObject;\n"+
				"\n"+
				"public class "+classname+" extends JavaScriptObject {\n"+
				"\n";
		str += "\tprotected "+classname+"(){}\n";
		str += "\tpublic static final "+classname+" newInstance(){\n";
		str += "\t\treturn "+classname+".eval(\"{}\");\n";
		str += "\t}\n";
		str += "\tpublic static final native "+classname+" eval(String json)/*-{\n";
		str += "\t\treturn eval('(' + json + ')');\n";
		str += "\t}-*/;\n";
		for(int i = 1 ; i <= mdata.getColumnCount();i++){
			String columnname = mdata.getColumnName(i).toLowerCase();
			String methodname = columnname.toUpperCase().charAt(0)+columnname.substring(1);
			String fieldclassname = this.translateGWT(mdata.getColumnClassName(i));
			str += "\tpublic final native void set"+methodname+"("+
					fieldclassname+" b) /*-{\n"+
					"\t\tthis.m_"+columnname+" = b;\n"+
					"\t}-*/;\n"+
					"\tpublic final native "+fieldclassname+" get"+methodname+"()/*-{\n"+
					"\t\treturn this.m_"+columnname+";\n"+
					"\t}-*/;\n";
		}
		str += "}";

		return str;
	}

	private String translateGWT(String columnClassName) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("java.math.BigDecimal", "Double");
		map.put("java.lang.String", "String");
		map.put("java.lang.Integer", "Integer");
		map.put("java.sql.Timestamp", "java.util.Date");
		map.put("java.lang.Boolean", "Boolean");
		map.put("java.lang.Short", "Integer");
		String retval = (String)map.get(columnClassName);
		if(retval == null){
			System.out.println("Unknown GWT class type, "+columnClassName);
			retval = "String";
		}
		return retval;
	}

	private String getGWTPackage() {
		return this.m_gwtpackage;
	}

	/**
	 * @return
	 */
	private String getDestination() {
		return this.m_destination;
	}

	/**
	 * @param mdata
	 * @throws SQLException 
	 */
	private String generateJDOManager(ResultSetMetaData mdata) throws SQLException {
		SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
		String fields = "";
		for(int i = 0;i < mdata.getColumnCount();i++){
			if(i > 0){
				fields += ",";
			}
			fields += "#TABLE#."+mdata.getColumnName(i+1).toUpperCase();
		}
		String className = this.getJdoClassName();
		String columntype = null;
		for(int i = 0; i < mdata.getColumnCount();i++){
			String name = mdata.getColumnName(i+1);
			if(name.equalsIgnoreCase(this.getIdentityField())){
				columntype = mdata.getColumnTypeName(i+1);
				this.setIdentifyFieldType(columntype,mdata.getColumnType(i+1));
				break;
			}
		}
		String str = "/*\n"+
				"* Created on "+df.format(new java.util.Date())+"\n"+
				"*\n"+
				"* Author: Ansel Robateau\n"+
				"*         http://www.robaone.com\n"+
				"*/\n"+
				"package "+this.getPackage()+";\n"+
				"\n"+
				"import java.math.BigDecimal;\n"+
				"import java.sql.Connection;\n"+
				"import java.sql.PreparedStatement;\n"+
				"import java.sql.ResultSet;\n"+
				"import java.sql.ResultSetMetaData;\n"+
				"import java.sql.SQLException;\n"+
				"import com.robaone.jdo.*;\n";
		if(this.m_jsonsupport){
			str += "import org.json.*;\n"+
					"import java.util.HashMap;\n"+
					"import java.util.Iterator;\n";
		}
		str += "\n"+this.getDBIncludes()+
				"public class "+className+"_jdoManager {\n"+
				"  private Connection m_con;\n"+
				"  private String m_options = \"\";\n"+
				"  private final static String SELECT = \"select ~ from #TABLE# #OPTION# "+
				" where "+this.getIdentityField()+" = ?\";\n"+
				"  private final static String INSERT = \"insert into #TABLE# "+
				"\";\n"+
				"  private final static String QUERY = \"select ~ from #TABLE# #OPTION# "+
				" where \";\n"+
				"  private final static String UPDATE = \"update #TABLE# set \""+
				";\n"+
				"  private final static String SEARCH = \"select COUNT(1) from #TABLE# #OPTION# where #TABLE#."+this.getIdentityField().toUpperCase()+" = ?\";\n"+
				"  private final static String DELETE = \"delete from #TABLE# "+
				"where #TABLE#."+this.getIdentityField().toUpperCase()+" = ?\";\n"+
				"  private final static String IDENTITY = \""+this.getIdentityField().toUpperCase()+"\";\n";
		if(!this.m_idmanager.equalsIgnoreCase("none")){
			str += "  private RO_JDO_IdentityManager<"+this.getIdentityManagerClass()+"> NEXT_SQL;\n";
		}
		str += "  public final static String FIELDS = \""+fields+
				"\";\n"+
				"  private String TABLE = \""+this.getTable().toUpperCase()+"\";\n"+
				"  protected boolean debug = false;\n"+
				"  public "+className+"_jdoManager(Connection con){\n"+
				"    this.m_con = con;\n"+
				"    this.setIdentityClass();\n"+
				"    try{\n"+
				"    \tif(System.getProperty(\"debug\").equals(\"Y\")){\n"+
				"    \t\tdebug = true;\n"+
				"    \t}\n"+
				"    }catch(Exception e){}\n"+
				"  }\n"+
				"  protected void setIdentityClass(){\n";
		if(!this.m_idmanager.equalsIgnoreCase("none")){
			str += "     this.NEXT_SQL = "+this.getNextSQL()+";\n";
		}
		str += "  }\n"+
				"  protected Connection getConnection(){\n"+
				"    return this.m_con;\n"+
				"  }\n"+
				"  public String getTableName(){\n"+
				"    return TABLE;\n"+
				"  }\n"+
				"  public void setTableName(String tablename){\n"+
				"    TABLE = tablename;\n"+
				"  }\n"+
				"  public "+className+"_jdo bind"+className+"(ResultSet rs) throws SQLExcep"+
				"tion{\n"+
				className+"_jdo retval = null;\n"+
				"    retval = "+className+"_jdoManager.createObject(rs);\n"+
				"    return retval;\n"+
				"  }\n"+
				"\n"+
				"  public "+className+"_jdo get"+className+"("+this.getIdentityClass()+" "+this.getIdentityField().toLowerCase()+"){\n"+
				"    PreparedStatement ps = null;\n"+
				"    ResultSet rs = null;\n"+
				"    "+className+"_jdo retval = null;\n"+
				"    try{\n"+
				"      String sql = this.getSQL(SELECT.split(\"[~]\")[0]+FIELDS+SELECT.split(\"[~]"+
				"\")[1]);\n"+
				"      ps = this.getConnection().prepareStatement(sql);\n"+
				"      ps.setObject(1,"+this.getIdentityField().toLowerCase()+");\n"+
				"      rs = ps.executeQuery();\n"+
				"      if(rs.next()){\n"+
				"        retval = "+className+"_jdoManager.createObject(rs);\n"+
				"      }\n"+
				"    }catch(Exception e){\n"+
				"\n"+
				"    }finally{\n"+
				"      try{rs.close();}catch(Exception e){}\n"+
				"      try{ps.close();}catch(Exception e){}\n"+
				"    }\n"+
				"    return retval;\n"+
				"  }\n"+
				"/**\n"+
				"* @param rs\n"+
				"* @return\n"+
				"* @throws SQLException\n"+
				"*/\n"+
				"  private static "+className+"_jdo createObject(ResultSet rs) throws SQLExcep"+
				"tion {\n"+
				"    "+className+"_jdo retval = null;\n"+
				"    retval = new "+className+"_jdo();\n"+
				"    /*\n"+
				"     *\n"+
				"     * Insert values from Result Set into object\n"+
				"     */\n"+
				"    ResultSetMetaData mdata = rs.getMetaData();\n"+
				"    for(int i = 0;i< mdata.getColumnCount();i++){\n"+
				"      String fieldname = mdata.getColumnName(i+1).toUpperCase();\n"+
				"      Object[] val = new Object[2];\n"+
				"      val[1] = new Boolean(false);\n"+
				"      // Set the value\n"+
				"      "+this.getSetValueLines()+
				"      retval.bindField(fieldname,val);\n"+
				"    }\n"+
				"\n"+
				"    return retval;\n"+
				"  }\n"+
				this.getSaveMethod();
		str += this.getHandlerMethods()+"\n";
		str += this.getSetAllCleanMethid();
		str += "  public void delete("+className+"_jdo record) throws Exception {"+
				"\n"+
				"    Connection con = this.getConnection();\n"+
				"    String sql_delete = this.getSQL(DELETE);\n"+
				"    PreparedStatement ps = con.prepareStatement(sql_delete);\n"+
				"    ps.setObject(1, record.getField(IDENTITY)[0]);\n"+
				"    int updated = ps.executeUpdate();\n"+
				"    if(debug) System.out.println(updated +\" records deleted.\");\n"+
				"  }\n";
		str += "  public PreparedStatement prepareStatement(String query) thro"+
				"ws SQLException{\n"+
				"    String sql = this.getSQL(QUERY.split(\"[~]\")[0]+FIELDS+QUERY.split(\"[~]\")"+
				"[1] + query);\n"+
				"    if(debug) System.out.println(sql);\n"+
				"    PreparedStatement ps = this.getConnection().prepareStatement"+
				"(sql);\n"+
				"    return ps;\n"+
				"  }\n"+
				this.getCreateMethod()+
				this.getSQLConvertMethod();
		if(this.m_jsonsupport == true){
			str += this.getJSONConvertMethod();
			str += this.getJSONBindMethod(mdata);
			str += this.getBindJSONMethod(mdata);
		}
		str += "}\n";
		return str;
	}

	private String getIdentityManagerClass() {
		if(this.getIdManagerClass().equals("RO_JDO_MySQL<Integer>")){
			return "Integer";
		}else if(this.getIdManagerClass().equals("RO_JDO_SQLServer<BigDecimal>")){
			return "BigDecimal";
		}else{
			return this.getIdentityClass();
		}
	}

	private String getNolockOption() {
		if(this.m_dbtype.equals("mysql")){
			return "";
		}else if(this.m_dbtype.equals("sqlserver")){
			return "with (nolock)";
		}else{
			return "";
		}
	}

	private String getSQLConvertMethod() {
		String str = "  public String getSQL(String sql){\n"+
				"    String retval = \"\";\n"+
				"    retval = sql.replaceAll(\"#TABLE#\",TABLE);\n"+
				"    retval = retval.replaceAll(\"#OPTION#\",this.getTableOptions());\n"+
				"    return retval;\n"+
				"  }\n";
		return str;
	}
	private String getJSONBindMethod(ResultSetMetaData mdata) throws SQLException{
		String str = "  public void bind"+this.getJdoClassName()+"JSON("+this.getJdoClassName()+"_jdo record,String jso"+
				"ndata) throws Exception {\n"+
				"    JSONObject jo = new JSONObject(jsondata);\n"+
				"    bind"+this.getJdoClassName()+"JSON(record,jo);\n"+
				"  }\n"+
				"  public void bind"+this.getJdoClassName()+"JSON("+this.getJdoClassName()+"_jdo record, JSONObjec"+
				"t jo) throws Exception {\n"+
				"    Iterator keys = jo.keys();\n"+
				"    HashMap keymap = new HashMap()"+
				";\n"+
				"    while(keys.hasNext()){\n"+
				"      String key = keys.next().toString();\n"+
				"      String lc_key = key.toUpperCase();\n"+
				"      keymap.put(lc_key, key);\n"+
				"    }\n"+
				"    if(record != null && jo != null){\n";
		for(int i = 1; i <= mdata.getColumnCount();i++){
			System.out.println("Processing: "+mdata.getColumnName(i)+":"+mdata.getColumnClassName(i)+":"+mdata.getColumnType(i)+":"+mdata.getColumnTypeName(i));
			str += "      try{\n"+
					"        if(jo.isNull((String)keymap.get("+this.getJdoClassName()+"_jdo."+mdata.getColumnName(i).toUpperCase()+"))){\n"+
					"          if(keymap.get("+this.getJdoClassName()+"_jdo."+mdata.getColumnName(i).toUpperCase()+") != null)\n"+
					"            record.set"+mdata.getColumnName(i).toUpperCase().charAt(0)+
					mdata.getColumnName(i).substring(1).toLowerCase()+"(null);\n"+
					"        }else{\n";
			if(mdata.getColumnClassName(i).equals("java.sql.Timestamp")){
				str += "          try{\n";
			}
			if(this.translate(mdata.getColumnTypeName(i),mdata.getColumnType(i)).equals("byte[]")){
				str += "            record.set"+mdata.getColumnName(i).toUpperCase().charAt(0)+
						mdata.getColumnName(i).substring(1).toLowerCase()+"(new com.robaone.json.JSONByteArray(jo.get"+
						"JSONArray((String)keymap.get("+this.getJdoClassName()+"_jdo."+mdata.getColumnName(i).toUpperCase()+
						"))).getBytes());\n";
			}else{
				str +=	"             record.set"+mdata.getColumnName(i).toUpperCase().charAt(0)+
						mdata.getColumnName(i).substring(1).toLowerCase()+"(new "+this.translate(mdata.getColumnTypeName(i),mdata.getColumnType(i))+
						"(jo.get"+this.translateJSON(mdata.getColumnClassName(i),mdata.getColumnType(i))+"((String)keymap.get("+this.getJdoClassName()+"_jdo."+
						mdata.getColumnName(i).toUpperCase()+
						"))));\n";
			}
			if(mdata.getColumnClassName(i).equals("java.sql.Timestamp")){
				str += "          }catch(JSONException e){\n"+
						"            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(\"yyyy-MM-dd kk:mm:ss.S\");\n"+
						"            long time = df.parse(jo.getString((String)keymap.get("+this.getJdoClassName()+"_jdo."+mdata.getColumnName(i).toUpperCase()+"))).getTime();\n"+
						"            record.set"+mdata.getColumnName(i).toUpperCase().charAt(0)+
						mdata.getColumnName(i).substring(1).toLowerCase()+"(new java.sql.Timestamp(time));\n"+
						"          }\n";
			}
			str +=  "        }\n"+
					"      }catch(org.json.JSONException e){\n"+
					//"        record.set"+mdata.getColumnName(i).toUpperCase().charAt(0)+mdata.getColumnName(i).toLowerCase().substring(1)+"(null);\n"+
					"      }\n";
		}
		str += "    }\n"+
				"  }\n";
		return str;
	}
	private String getBindJSONMethod(ResultSetMetaData mdata) throws SQLException{
		String jsonclass = null;
		for(int i = 1; i <= mdata.getColumnCount();i++){
			String columnname = mdata.getColumnName(i);
			if(columnname.equalsIgnoreCase(this.getIdentityField())){
				jsonclass = this.translateJSON(mdata.getColumnClassName(i),mdata.getColumnType(i));
				break;
			}
		}
		String str = "\tpublic "+this.getJdoClassName()+"_jdo bind"+
				this.getJdoClassName()+"JSON(String jsondata) throws Exce"+
				"ption {\n"+
				"\t\t"+this.getJdoClassName()+"_jdo retval = null;\n"+
				"\t\tJSONObject jo = new JSONObject(jsondata);\n"+
				"\t\tretval = this.bind"+this.getJdoClassName()+"JSON(jo);\n"+
				"\t\treturn retval;\n"+
				"\t}\n"+
				"\tprivate "+this.getJdoClassName()+"_jdo bind"+this.getJdoClassName()+
				"JSON(JSONObject jo) throws Excep"+
				"tion{\n"+
				"\t\tIterator keys = jo.keys();\n"+
				"\t\tHashMap keymap = new HashMap"+
				"();\n"+
				"\t\twhile(keys.hasNext()){\n"+
				"\t\t\tString key = keys.next().toString();\n"+
				"\t\t\tString lc_key = key.toUpperCase();\n"+
				"\t\t\tkeymap.put(lc_key, key);\n"+
				"\t\t}\n"+
				"\t\t"+this.getJdoClassName()+"_jdo record = null;\n"+
				"\t\ttry{\n"+
				"\t\t\tif(!jo.isNull((String)keymap.get(IDENTITY))){\n"+
				"\t\t\trecord = this.get"+this.getJdoClassName()+
				"(new "+this.getIdentityClass()+"(jo.get"+jsonclass+
				"((String)keymap.get(IDENTITY))));\n"+
				"\t\t\t}\n"+
				"\t\t}catch(JSONException e){}\n"+
				"\t\tif(record == null){\n"+
				"\t\t\trecord = this.new"+this.getJdoClassName()+"();\n"+
				"\t\t}\n"+
				"\t\tbind"+this.getJdoClassName()+
				"JSON(record, jo);\n"+
				"\t\treturn record;\n"+
				"\t}\n";
		return str;
	}
	private String translateJSON(String cn,int cn_type) {
		if(cn_type == -9){
			cn = "java.sql.Timestamp";
		}
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("java.lang.Integer", "Int");
		map.put("java.lang.String", "String");
		map.put("java.math.BigDecimal","Double");
		map.put("java.sql.Timestamp", "Long");
		map.put("java.lang.Short", "Int");
		map.put("java.lang.Boolean","Boolean");
		map.put("byte[]", "JSONArray");
		String retval = (String)map.get(cn);
		if(retval == null){
			retval = "String";
			System.out.println("Unknown json type for "+cn);
		}
		return retval;
	}

	private String getJSONConvertMethod() {
		String str = "  public JSONObject toJSONObject("+this.getJdoClassName()+"_jdo record) throws E"+
				"xception {\n"+
				"    JSONObject retval = null;\n"+
				"    if(record != null){\n"+
				"      JSONObject object = new JSONObject();\n"+
				"      for(int i = 0; i < record.getFieldCount(); i++){\n"+
				"        String fieldname = record.getField(i);\n"+
				"        Object value = record.getField(fieldname)[0];\n"+
				"        object.put(fieldname.toLowerCase(), value);\n"+
				"      }\n"+
				"      retval = object;\n"+
				"    }\n"+
				"    return retval;\n"+
				"  }\n";
		return str;
	}

	private String getIdentityClass() {
		return this.m_identity_class;
	}

	private void setIdentifyFieldType(String columntype,int columntypeid) {
		this.m_identity_class = this.translate(columntype,columntypeid);
	}

	private String getDBIncludes() {

		if(this.m_dbtype != null && this.m_dbtype.equals("oracle")){
			String str = "\n"+
					"import oracle.jdbc.OraclePreparedStatement;\n";
			return str;
		}
		return "";
	}

	private String getSaveMethod() {
		String classname = this.getJdoClassName();
		String str = ""+
				"  private void setTableOptions(String str){\n"+
				"    this.m_options = str;\n"+
				"  }\n"+
				"  private String getTableOptions(){\n"+
				"    return this.m_options;\n"+
				"  }\n"+
				"  public void save("+classname+"_jdo record) throws Exception {\n"+
				"    this.save(record,false);\n"+
				"  }\n"+
				"  public void save("+classname+"_jdo record,boolean dirty) throws Exception {\n"+
				"    Connection con = this.getConnection();\n"+
				"    if(dirty){\n"+
				"      this.setTableOptions(\""+this.getNolockOption()+"\");\n"+
				"    }\n"+
				"    boolean finished = false;\n"+
				"    if(record.getDirtyFieldCount() == 0){\n"+
				"      return;\n"+
				"    }\n"+
				"    try{\n"+
				"      String search = this.getSQL(SEARCH);\n"+
				"      int count = 0;\n"+
				"      if(record.getField(record.getIdentityName()) != null  && record.getField(record.getIdentityName())[0] != null){\n"+
				"        PreparedStatement ps = con.prepareStatement(search);\n"+
				"        ps.setObject(1,record.getField(record.getIdentityName())[0])"+
				";\n"+
				"        ResultSet rs = ps.executeQuery();\n"+
				"        if(rs.next()){\n"+
				"          count = rs.getInt(1);\n"+
				"        }\n"+
				"        rs.close();\n"+
				"        ps.close();\n"+
				"      }\n"+
				"      if(count > 0){\n"+
				"      // Update\n"+
				"        if(debug) System.out.println(\"Updating...\");\n"+
				"        this.handleBeforeUpdate(record);\n"+
				"        String update_sql = this.getSQL(UPDATE);\n"+
				"        int dirtyfieldcount = 0;\n"+
				"        for(int i = 0; i < record.getDirtyFieldCount();i++){\n"+
				"          String fieldname = record.getDirtyField(i);\n"+
				"          if(i > 0){\n"+
				"            update_sql += \", \";\n"+
				"          }\n"+
				"          update_sql += fieldname +\" = ?\";\n"+
				"          dirtyfieldcount ++;\n"+
				"        }\n"+
				"        update_sql += \" where \"+record.getIdentityName()+\" = ?\";\n"+
				"        PreparedStatement update_ps = con.prepareStatement(update_sq"+
				"l);\n"+
				"\n"+
				"        for(int i = 0; i < record.getDirtyFieldCount();i++){\n"+
				"          Object value = record.getField(record.getDirtyField(i))[0];\n"+
				"          if(value instanceof java.sql.Timestamp){\n"+
				"            update_ps.setObject(i+1,value);\n"+
				"          }else if(value instanceof java.sql.Date){\n"+
				"            update_ps.setObject(i+1,new java.sql.Timestamp(((java.sql.Date)value).getTime()));\n"+
				"          }else if(value instanceof java.util.Date){\n"+
				"            value = new java.sql.Timestamp(((java.util.Date)value).getTime())"+
				";\n"+
				"            update_ps.setObject(i+1,value);\n"+
				"          }";
		if(this.m_dbtype != null && this.m_dbtype.equals("oracle")){
			str += "else if(value instanceof String){\n"+
					"          if(((String) value).length() > 32765){\n"+
					"            OraclePreparedStatement ops = (OraclePreparedStatement)updat"+
					"e_ps;\n"+
					"            ops.setStringForClob(i+1, (String)value);\n"+
					"          }else{\n"+
					"            update_ps.setString(i+1, (String)value);\n"+
					"          }\n        }";
		}
		str += "else{\n"+
				"            update_ps.setObject(i+1,value);\n"+
				"          }\n"+
				"        }\n"+
				"        update_ps.setObject(dirtyfieldcount+1,record.getField(record"+
				".getIdentityName())[0]);\n"+
				"        int updated = update_ps.executeUpdate();\n"+
				"        finished = true;\n"+
				"        if(updated == 0){\n"+
				"          throw new Exception(\"No rows updated.\");\n"+
				"        }\n"+
				"        if(debug) System.out.println(updated +\" rows updated.\");\n"+
				"        this.handleAfterUpdate(record);\n"+
				"        /**\n"+
				"         * Mark all fields as clean\n"+
				"         */\n"+
				"        this.setAllClean(record);\n"+
				"        update_ps.close();\n"+
				"      }else{\n"+
				"        // Insert\n"+
				"        if(debug) System.out.println(\"Inserting...\");\n";
		if(this.m_idmanager.equalsIgnoreCase("identity")){
			str = identityInsertAssignment(str);
		}else if(!this.m_idmanager_class.equalsIgnoreCase("none")){
			str = prefillInsertAssignment(str);
		}else{
			str = noIdentityInsertAssignment(str);
		}
		str += "        int updated = insert_ps.executeUpdate();\n";
		if(!this.m_idmanager.equalsIgnoreCase("none") && !this.m_idmanager.equalsIgnoreCase("identity")){
			str += "        record.set"+this.getIdentityField().toUpperCase().charAt(0)+this.getIdentityField().toLowerCase().substring(1)+"(idval);\n";
		}else if(this.m_idmanager.equalsIgnoreCase("identity")){
			str += "        record.set"+this.getIdentityField().toUpperCase().charAt(0)+this.getIdentityField().toLowerCase().substring(1)+"("+getIdValue()+");\n";
		}
		str += "        finished = true;\n"+
				"        if(updated == 0){\n"+
				"          throw new Exception(\"No rows added.\");\n"+
				"        }else{\n"+
				"          this.handleAfterInsert(record);\n"+
				"          this.setAllClean(record);\n"+
				"        }\n"+
				"        if(debug) System.out.println(updated+\" rows added.\");\n"+
				"        insert_ps.close();\n"+
				"      }\n"+
				"    }finally{}\n"+
				"  }\n";
		return str;
	}

	private String prefillInsertAssignment(String str) {
		str +="        String insert_sql = this.getSQL(INSERT);\n";
		if(!this.m_idmanager.equalsIgnoreCase("none")){
			str += "        "+this.getIdentityClass()+" idval = NEXT_SQL.getIdentity(this.getTableName(),this.m_con);\n";
		}
		str += "        String insert_pre,insert_post;\n"+
				"        insert_pre = \"(\"; insert_post = \"(\";\n"+
				"        for(int i = 0;i < record.getFieldCount();i++){\n"+
				"          String fieldname = record.getField(i);\n"+
				"          if(i > 0){\n"+
				"            insert_pre += \",\";\n"+
				"            insert_post += \",\";\n"+
				"          }\n"+
				"          insert_pre += fieldname;\n"+
				"          if(fieldname.equalsIgnoreCase(record.getIdentityName())){\n"+
				"            insert_post += \"?\";\n"+
				"          }else{\n"+
				"            insert_post += \"?\";\n"+
				"          }\n"+
				"        }\n"+
				"        insert_pre += \") values \";\n"+
				"        insert_post += \")\";\n"+
				"        insert_sql += insert_pre + insert_post;\n"+
				"        PreparedStatement insert_ps = con.prepareStatement(insert_sq"+
				"l);\n"+
				"        int field_index = 1;\n"+
				"        for(int i = 0; i < record.getFieldCount();i++){\n"+
				"          String fieldname = record.getField(i);\n"+
				"          Object[] val = record.getField(fieldname);\n";
		if(!this.m_idmanager.equalsIgnoreCase("none")){
			str += "          if(fieldname.equalsIgnoreCase(record.getIdentityName())){\n"+
					"            insert_ps.setObject(field_index,idval);\n"+
					"            field_index++;\n"+
					"            continue;\n"+
					"          }\n";
		}
		str += "          if(val[0] instanceof java.sql.Timestamp){\n"+
				"            insert_ps.setObject(field_index,val[0]);\n"+
				"          }else if(val[0] instanceof java.sql.Date){\n"+
				"            insert_ps.setObject(field_index,new java.sql.Timestamp(((java.sql.Date)val[0]).getTime()));\n"+
				"          }else\n"+
				"            if(val[0] instanceof java.util.Date){\n"+
				"              val[0] = new java.sql.Date(((java.util.Date)val[0]).getTime("+
				"));\n"+
				"              insert_ps.setObject(field_index,val[0]);\n";
		if(this.m_dbtype != null && this.m_dbtype.equals("oracle")){
			str +="}else if(val[0] instanceof String){\n"+
					"             if(((String) val[0]).length() > 32765){\n"+
					"               OraclePreparedStatement ops = (OraclePreparedStatement)val[0"+
					"];\n"+
					"               ops.setStringForClob(field_index, (String)val[0]);\n"+
					"             }else{\n"+
					"               insert_ps.setString(field_index, (String)val[0]);\n"+
					"             }\n";
		}
		str += "          }else{\n"+
				"            insert_ps.setObject(field_index,val[0]);\n"+
				"          }\n"+
				"          field_index ++;\n"+
				"        }\n";
		return str;
	}

	private String noIdentityInsertAssignment(String str) {
		str +="        String insert_sql = this.getSQL(INSERT);\n"+
				"        String insert_pre,insert_post;\n"+
				"        insert_pre = \"(\"; insert_post = \"(\";\n"+
				"        for(int i = 0;i < record.getFieldCount();i++){\n"+
				"          String fieldname = record.getField(i);\n"+
				"          if(i > 0){\n"+
				"            insert_pre += \",\";\n"+
				"            insert_post += \",\";\n"+
				"          }\n"+
				"          insert_pre += fieldname;\n"+
				"          if(fieldname.equalsIgnoreCase(record.getIdentityName())){\n"+
				"            continue;\n"+
				"          }else{\n"+
				"            insert_post += \"?\";\n"+
				"          }\n"+
				"        }\n"+
				"        insert_pre += \") values \";\n"+
				"        insert_post += \")\";\n"+
				"        insert_sql += insert_pre + insert_post;\n"+
				"        PreparedStatement insert_ps = con.prepareStatement(insert_sq"+
				"l);\n"+
				"        int field_index = 1;\n"+
				"        for(int i = 0; i < record.getFieldCount();i++){\n"+
				"          String fieldname = record.getField(i);\n"+
				"          if(fieldname.equalsIgnoreCase(record.getIdentityName())){\n"+
				"            continue;\n"+
				"          }\n"+
				"          Object[] val = record.getField(fieldname);\n"+
				"          if(val[0] instanceof java.sql.Timestamp){\n"+
				"            insert_ps.setObject(field_index,val[0]);\n"+
				"          }else if(val[0] instanceof java.sql.Date){\n"+
				"            insert_ps.setObject(field_index,new java.sql.Timestamp(((java.sql.Date)val[0]).getTime()));\n"+
				"          }else\n"+
				"            if(val[0] instanceof java.util.Date){\n"+
				"              val[0] = new java.sql.Date(((java.util.Date)val[0]).getTime("+
				"));\n"+
				"              insert_ps.setObject(field_index,val[0]);\n";
		if(this.m_dbtype != null && this.m_dbtype.equals("oracle")){
			str +="}else if(val[0] instanceof String){\n"+
					"             if(((String) val[0]).length() > 32765){\n"+
					"               OraclePreparedStatement ops = (OraclePreparedStatement)val[0"+
					"];\n"+
					"               ops.setStringForClob(field_index, (String)val[0]);\n"+
					"             }else{\n"+
					"               insert_ps.setString(field_index, (String)val[0]);\n"+
					"             }\n";
		}
		str += "          }else{\n"+
				"            insert_ps.setObject(field_index,val[0]);\n"+
				"          }\n"+
				"          field_index ++;\n"+
				"        }\n";
		return str;
	}

	private String identityInsertAssignment(String str) {
		str +="        String insert_sql = this.getSQL(INSERT);\n"+
				"        String insert_pre,insert_post;\n"+
				"        insert_pre = \"(\"; insert_post = \"(\";\n"+
				"        for(int i = 0;i < record.getFieldCount();i++){\n"+
				"          String fieldname = record.getField(i);\n"+
				"          if(fieldname.equalsIgnoreCase(record.getIdentityName())){\n"+
				"            continue;\n"+
				"          }\n"+
				"          if(i > 0 && insert_pre.length() > 1 && insert_post.length() > 1){\n"+
				"            insert_pre += \",\";\n"+
				"            insert_post += \",\";\n"+
				"          }\n"+
				"          insert_pre += fieldname;\n"+
				"          insert_post += \"?\";\n"+
				"        }\n"+
				"        insert_pre += \") values \";\n"+
				"        insert_post += \")\";\n"+
				"        insert_sql += insert_pre + insert_post;\n"+
				"        PreparedStatement insert_ps = con.prepareStatement(insert_sq"+
				"l);\n"+
				"        int field_index = 1;\n"+
				"        for(int i = 0; i < record.getFieldCount();i++){\n"+
				"          String fieldname = record.getField(i);\n"+
				"          if(fieldname.equalsIgnoreCase(record.getIdentityName())){\n"+
				"            continue;\n"+
				"          }\n"+
				"          Object[] val = record.getField(fieldname);\n"+
				"          if(val[0] instanceof java.sql.Timestamp){\n"+
				"            insert_ps.setObject(field_index,val[0]);\n"+
				"          }else if(val[0] instanceof java.sql.Date){\n"+
				"            insert_ps.setObject(field_index,new java.sql.Timestamp(((java.sql.Date)val[0]).getTime()));\n"+
				"          }else\n"+
				"            if(val[0] instanceof java.util.Date){\n"+
				"              val[0] = new java.sql.Date(((java.util.Date)val[0]).getTime("+
				"));\n"+
				"              insert_ps.setObject(field_index,val[0]);\n";
		str += "          }else{\n"+
				"            insert_ps.setObject(field_index,val[0]);\n"+
				"          }\n"+
				"          field_index ++;\n"+
				"        }\n";
		return str;
	}
	private String getIdValue() {
		// NEXT_SQL.getIdentity(this.m_con)
		String retval = "new "+this.getIdentityClass()+"(NEXT_SQL.getIdentity(this.getTableName(),this.m_con).toString())";
		return retval;
	}

	private String getHandlerMethods() {
		String str = "\tprotected void handleAfterInsert("+this.getJdoClassName()+"_jdo record) {}\n"+
				"\tprotected void handleAfterUpdate("+this.getJdoClassName()+"_jdo record) {}\n"+
				"\tprotected void handleBeforeUpdate("+this.getJdoClassName()+"_jdo record) {}";
		return str;
	}

	private String getSetAllCleanMethid() {
		String classname = this.getJdoClassName();
		String str = ""+
				"  private void setAllClean("+classname+"_jdo record){\n"+
				"    try{\n"+
				"      for(int i = 0; i < record.getFieldCount();i++){\n"+
				"         String fieldname = record.getField(i);\n"+
				"         Object[] val = record.getField(fieldname);\n"+
				"         if(val != null)\n"+
				"           val[1] = new Boolean(false);\n"+
				"      }\n"+
				"    }catch(Exception e){}\n"+
				"  }\n";
		return str;
	}
	private String getCreateMethod() {
		String className = this.getJdoClassName();
		String str = "public "+className+"_jdo new"+className+"() {\n"+
				className+"_jdo retval = new "+className+"_jdo();\n"+
				" retval.set"+this.getIdentityField().toUpperCase().charAt(0)+this.getIdentityField().toLowerCase().substring(1)+"(null);\n"+
				" return retval;\n"+
				"}\n";
		return str;
	}

	private String getNextSQL() {
		if(this.getIdManagerClass() == null){
			if(this.m_dbtype.equals("mysql")){
				return "new RO_JDO_MySQL<"+this.getIdentityManagerClass()+">()";
			}else if(this.m_dbtype.equals("sqlserver")){
				return "new RO_JDO_SQLServer<"+this.getIdentityManagerClass()+">()";
			}else{
				return "null";
			}
		}else{
			return "new "+this.getIdManagerClass()+"()";
		}
	}
	private String getIdManagerClass(){
		if(m_idmanager_class == null){
			if(this.m_dbtype.equals("mysql")){
				this.m_idmanager_class = "RO_JDO_MySQL<Integer>";
			}else if(this.m_dbtype.equals("sqlserver")){
				this.m_idmanager_class = "RO_JDO_SQLServer<BigDecimal>";
			}
		}
		return this.m_idmanager_class;
	}
	private String getSetValueLines() {
		String retval = "\t\tval[0] = rs.getObject(i+1);\n";
		retval += "\t\tif(val[0] instanceof java.sql.Date){\n"+
				"\t\t\tval[0] = new java.util.Date(rs.getTimestamp(i+1).getTime());\n"+
				"\t\t}\n";
		if(this.m_dbtype != null){
			if(this.m_dbtype.equalsIgnoreCase("sqlserver")){

			}else if(this.m_dbtype.equalsIgnoreCase("oracle")){
				retval += "\t\tif(val[0] instanceof oracle.sql.BLOB){\n"+
						"\t\t\tval[0] = ((oracle.sql.BLOB)val[0]).getBytes();\n"+
						"\t\t}else if(val[0] instanceof oracle.sql.CLOB){\n"+
						"\t\t\tval[0] = rs.getString(i+1);\n"+
						"\t\t}\n";
			}
		}
		return retval;
	}

	/**
	 * @return
	 */
	private String getPackage() {
		return this.m_package;
	}

	/**
	 * @param mdata
	 * @throws SQLException
	 */
	private String generateJDO(ResultSetMetaData mdata) throws SQLException {
		SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
		String className = this.getJdoClassName();
		String str = "/*\n"+
				"* Created on "+df.format(new java.util.Date())+"\n"+
				"* Author Ansel Robateau\n"+
				"* http://www.robaone.com\n"+
				"*\n"+
				"*/\n"+
				"package "+this.getPackage()+";\n"+
				"\n"+
				"import java.math.BigDecimal;\n"+
				"import java.util.Date;\n"+
				"import com.robaone.jdo.RO_JDO;\n"+
				"\n"+
				"\n"+
				"public class "+className+"_jdo extends RO_JDO{\n";
		for(int i = 0; i < mdata.getColumnCount();i++){
			str += "  public final static String "+mdata.getColumnName(i+1).toUpperCase()+" = \""+mdata.getColumnName(i+1).toUpperCase()+"\";\n";
		}
		str +=	"  protected "+className+"_jdo(){\n"+
				"    \n"+
				"  }\n";
		for(int i = 0; i < mdata.getColumnCount();i++){
			String fieldName = mdata.getColumnName(i+1).toUpperCase().charAt(0)+mdata.getColumnName(i+1).toLowerCase().substring(1);
			String columntype = mdata.getColumnTypeName(i+1);
			int columntypeid = mdata.getColumnType(i+1);
			str += this.getSetMethod(fieldName,columntype,columntypeid);
			str += this.getGetMethod(fieldName, columntype,columntypeid);
		}
		str += "  public String getIdentityName() {\n"+
				"    return \""+this.getIdentityField().toUpperCase()+"\";\n"+
				"  }\n";/*
		str += "  protected void set"+this.getIdentityField().toUpperCase().charAt(0)+this.getIdentityField().substring(1).toLowerCase()+"(BigDecimal bigDecimal){\n"+
	           "    this.setField(this.getIdentityName(), bigDecimal);\n"+
			   "  }\n";
				 */
		str += "}";
		return str;
	}

	private String getJdoClassName() {
		String retval = this.getTable().toUpperCase().charAt(0)+this.getTable().toLowerCase().substring(1);
		try {
			retval = Strmanip.replacechar(retval, '.', '_');
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retval;
	}

	private String getSetMethod(String fieldName, String columntype,int columntypeid) {
		String access = "  public";
		if(fieldName.equalsIgnoreCase(this.getIdentityField()) && !this.m_idmanager.equals("none")){
			access = "  protected";
		}
		String str = access+" void set"+fieldName+"("+this.translate(columntype,columntypeid)+" "+fieldName.toLowerCase()+"){\n"+
				"    this.setField("+fieldName.toUpperCase()+","+fieldName.toLowerCase()+");\n"+
				"  }\n";
		return str;
	}
	private String getGetMethod(String fieldName, String columntype,int columntypeid) {
		String mode = "";
		if(fieldName.equalsIgnoreCase(this.getIdentityField())){
			mode = "final ";
		}
		String str = "  public "+mode+this.translate(columntype,columntypeid)+" get"+fieldName+"(){\n"+
				"    Object[] val = this.getField("+fieldName.toUpperCase()+");\n"+
				"    if(val != null && val[0] != null){\n"+
				this.getObjectReturn(this.translate(columntype,columntypeid))+
				"    }else{\n"+
				"      return null;\n"+
				"    }\n"+
				"  }\n";
		return str;
	}

	private String getObjectReturn(String type) {
		String space = "      ";
		String retval = space;
		if(type.equalsIgnoreCase("Integer")){
			retval += "if(val[0] instanceof java.lang.Short){\n";
			retval += space+"  return new Integer(((java.lang.Short)val[0]).toString());\n";
			retval += space+"}else{\n";
			retval += space+"  return (Integer)val[0];\n";
			retval += space+"}\n";
		}else{
			retval += "return ("+type+")val[0];\n";
		}
		return retval;
	}

	private String translate(String columntype,int columntypeid) {
		HashMap<String,String> map = new HashMap<String,String>();
		/*
		 * nvarchar->String
		   int->Integer
           text->String
           bit->Boolean
		 */
		columntype = columntype.toLowerCase();
		if(columntypeid == -9){
			columntype = "datetime";
		}
		map.put("int identity", "Integer");
		map.put("smallint", "Integer");
		map.put("datetime", "java.sql.Timestamp");
		map.put("sys.xmltype", "Object");
		map.put("binary_float","Object");
		map.put("binary_double", "Object");
		map.put("float", "Double");
		map.put("intervalds", "Object");
		map.put("intervalym", "Object");
		map.put("bfile", "byte[]");
		map.put("clob", "String");
		map.put("blob","byte[]");
		map.put("raw", "byte[]");
		map.put("date", "java.util.Date");
		map.put("char", "String");
		map.put("varchar2", "String");
		map.put("varchar","String");
		map.put("number", "BigDecimal");
		map.put("decimal", "BigDecimal");
		map.put("nvarchar", "String");
		map.put("numeric", "BigDecimal");
		map.put("int", "Integer");
		map.put("integer", "Integer");
		map.put("text", "String");
		map.put("bit","Boolean");
		map.put("tinyint", "Integer");
		map.put("long", "Long");
		map.put("timestamp", "java.sql.Timestamp");
		map.put("ntext", "String");
		map.put("varbinary", "byte[]");
		map.put("uniqueidentifier", "String");
		String retval = (String) map.get(columntype);
		if(retval == null){
			System.out.println("Could not map columntype = '"+columntype+"'");
			retval = "Object";
		}
		return retval;
	}

	/**
	 * @return
	 */
	private String getIdentityField() {
		return this.m_identity;
	}

	private void setIdentityField(String id){
		this.m_identity = id;
	}

	/**
	 * @return
	 */
	private String getTable() {
		return this.m_table;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	private void processArguments(String[] args) throws Exception {
		if(args.length < 1){
			throw new Exception("Not enough arguments");
		}
		try{
			for(int i = 0; i < 20;i++){
				String str = "JAVA-PARM"+(i+1);
				try{
					String val = System.getProperty(str);
					if(val != null){
						this.m_mnemonics.put(str, val);
					}
				}catch(Exception e){}
			}
		}catch(Exception e){}

		INIFileReader ini = new INIFileReader(args[0]);
		String driver = this.replaceMnemonics(ini.getValue("driver"));
		String url = this.replaceMnemonics(ini.getValue("url"));
		String username = this.replaceMnemonics(ini.getValue("username"));
		String password = this.replaceMnemonics(ini.getValue("password"));
		String source_package = null;
		String destination = null;
		try{
			source_package = this.replaceMnemonics(ini.getValue("package"));
		}catch(Exception e){throw new Exception("Variable 'package' missing from config file.");}
		try{
			destination = this.replaceMnemonics(ini.getValue("destination"));
		}catch(Exception e){throw new Exception("Variable 'destination' missing from config file.");}
		String gwtpackage = null;
		String gwtdestination = null;
		try{gwtpackage = this.replaceMnemonics(ini.getValue("gwtpackage"));}catch(Exception e){}
		try{gwtdestination = this.replaceMnemonics(ini.getValue("gwtdestination"));}catch(Exception e){}
		this.setPackage(source_package);
		this.setDestination(destination);
		if(gwtpackage != null && gwtdestination != null){
			this.setGWTPackage(gwtpackage);
			this.setGWTDestination(gwtdestination);
		}
		try{
			if(url.startsWith("jdbc:oracle")){
				this.m_dbtype = "oracle";
			}else if(url.startsWith("jdbc:mysql")){
				this.m_dbtype = "mysql";
			}else if(url.startsWith("jdbc:sqljdbc")){
				this.m_dbtype = "sqlserver";
			}else if(driver.startsWith("com.microsoft.sqlserver")){
				this.m_dbtype = "sqlserver";
			}
		}catch(Exception e){}
		try{
			String jsonsupport = this.replaceMnemonics(ini.getValue("json_support"));
			if(jsonsupport.equalsIgnoreCase("yes") || jsonsupport.equalsIgnoreCase("true") || jsonsupport.equalsIgnoreCase("y")){
				this.m_jsonsupport = true;
			}
		}catch(Exception e){}
		Connection con = null;//BatchProcessing.getDBConnection(server,name,username,password);
		con = com.robaone.dbase.DBManager.getConnection(driver, url, username, password);
		this.setConnection(con);

		String tables = this.replaceMnemonics(ini.getValue("tables"));
		String[] tablearray = tables.split("[,]");
		for(int i = 0; i < tablearray.length;i++){
			String table = tablearray[i];
			this.setTable(table);
			try{
				this.m_fields.clear();
				String fields = this.replaceMnemonics(ini.getValue(table+".fields"));
				if(!fields.trim().equals("*")){
					String[] fieldnames = fields.split("[,]");
					for(int j = 0; j < fieldnames.length;j++){
						this.m_fields.add(fieldnames[j]);
					}
				}
			}catch(Exception e){throw new Exception("Missing "+table+".fields");}
			try{
				String identity = this.replaceMnemonics(ini.getValue(table+".identity"));
				this.setIdentityField(identity);
			}catch(Exception e){throw new Exception("Missing "+table+".identity");}
			try{
				String idmanager = this.replaceMnemonics(ini.getValue(table+".idmanager"));
				this.setIdManager(idmanager);
			}catch(Exception e){throw new Exception("Missing "+table+".idmanager");}
			try{
				String idmanager_class = this.replaceMnemonics(ini.getValue(table+".idmanager_class"));
				this.setIdManagerClass(idmanager_class);
			}catch(Exception e){}
			this.createClasses();
		}

	}

	private void setIdManagerClass(String idmanager_class) {
		this.m_idmanager_class  = idmanager_class;
	}

	private void setGWTPackage(String string) {
		this.m_gwtpackage = string;
	}

	private void setIdManager(String idmanager) {
		this.m_idmanager = idmanager;
	}
	private void setGWTDestination(String string) {
		String[] package_path = this.getGWTPackage().split("\\.");
		this.m_gwtdestination = string;
		for(int i = 0;i < package_path.length;i++){
			this.m_gwtdestination += System.getProperty("file.separator");
			this.m_gwtdestination += package_path[i];
		}
	}
	/**
	 * @param string
	 */
	private void setDestination(String string) {
		String[] package_path = this.getPackage().split("\\.");
		this.m_destination = string;
		for(int i = 0;i < package_path.length;i++){
			this.m_destination += System.getProperty("file.separator");
			this.m_destination += package_path[i];
		}
	}

	/**
	 * @param string
	 */
	private void setPackage(String string) {
		this.m_package = string;	
	}

	/**
	 * @param string
	 */
	private void setTable(String string) {
		this.m_table = string;
	}

	/**
	 * @param con
	 */
	private void setConnection(Connection con) {
		this.m_con = con;
	}
	private Connection getConnection(){
		return this.m_con;
	}
	/**
	 * 
	 */
	private static void showUsage() {
		System.out.println("Usage: [jdbc config file]");
		System.out.println("  jdbc config file : This file has the driver name, url,authentication and table schemas.");
		System.out.println("                     See sample config file.");
		System.out.println("\n\tCONFIG_FILE");
		System.out.println("\tdriver=");
		System.out.println("\turl=");
		System.out.println("\tusername=");
		System.out.println("\tpassword=");
		System.out.println("\tpackage=");
		System.out.println("\tdestination=");
		System.out.println("\tgwtpackage=");
		System.out.println("\tgwtdestination=");
		System.out.println("\ttables=tablename");
		System.out.println("\ttablename.fields=*");
		System.out.println("\ttablename.identity=fieldname");
		System.out.println("\ttablename.idmanager=identity or none");
		System.out.println("\ttablename.idmanager_class=(optional) class name");
		System.out.println("\tjson_support=[true or false]");
	}
	protected String replaceMnemonics(String value) {
		try{
			Object[] keys = this.m_mnemonics.keySet().toArray();
			for(int i = 0; i < keys.length;i++){
				Object mnemonic = keys[i];
				String mnemonic_value = (String)this.m_mnemonics.get(mnemonic);
				value = value.replaceAll("%"+mnemonic+"%", mnemonic_value);
			}
			return value;
		}catch(Exception e){
			return value;
		}
	}
}
