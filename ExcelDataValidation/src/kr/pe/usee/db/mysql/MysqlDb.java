package kr.pe.usee.db.mysql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import kr.pe.usee.db.excel.KbfAttr;
import kr.pe.usee.db.excel.KbfAttrValueList2;
import kr.pe.usee.db.excel.Prod2;
import kr.pe.usee.excel.AttrResultSet;
import kr.pe.usee.excel.GrpResultSet;
import kr.pe.usee.excel.Kbf;
import kr.pe.usee.excel.KbfAttrList;
import kr.pe.usee.excel.KbfAttrValueList;
import kr.pe.usee.excel.PogRed;
import kr.pe.usee.excel.PogRow;
import kr.pe.usee.excel.Prod;
import kr.pe.usee.excel.ProdInfo;
import kr.pe.usee.excel.SelectedData;

public class MysqlDb {

	
	// JDBC driver name and database URL
 	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
  
	Connection conn = null;

	String connectionUrl = "jdbc:mysql://localhost:3306/group?useUnicode=yes&characterEncoding=UTF-8";
	String connectionUser = "root";
	String connectionPassword = "apmsetup";

	public MysqlDb() {
		
	}
	
	public MysqlDb(String connectionUrl, String id, String pw) {
		this.connectionUrl = connectionUrl;
		this.connectionUser = id;
		this.connectionPassword = pw;
	}

	public void getConn() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

		Class.forName(JDBC_DRIVER).newInstance();
		conn = DriverManager.getConnection(this.connectionUrl, this.connectionUser, this.connectionPassword);

		//System.out.println("Hello connect MySQL!");
	}
	

	public void getConn2() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

		Class.forName(JDBC_DRIVER).newInstance();
		conn = DriverManager.getConnection(this.connectionUrl, this.connectionUser, this.connectionPassword);

		//System.out.println("Hello connect MySQL!");
	}

	public void closeConn() throws SQLException {
		 
		conn.close();
		try
		{
			if(conn!=null)
				conn.close();
		}catch(SQLException se){
		       se.printStackTrace();
		}//end finally try
		//System.out.println("Goodbye!");

	}

	/*
	public void upsert(ListRow [] list) throws SQLException, UnsupportedEncodingException {
		//conn.setAutoCommit(false);
		TobeTable tobe;
		TobeTable tobeDb;
		AsisTable asisDb;
		
	//	try 
		{
			for(ListRow row : list) {
				tobe = row.getTobeTable();
				tobeDb = selectTobeFromDb(conn, tobe);
				if(tobeDb== null) {
					insertTobe(conn, row);
				} else {
					updateTobe(conn, row);
				}
				
				for(AsisTable asisTable : row.getAsisTables()) {
					asisDb = selectAsisFromDb(conn, asisTable);
					if(asisDb == null) {
						insertAsis(conn,asisTable);
					} else {
						updateAsis(conn,asisTable);
					}

					upsertMap(conn,tobe,asisTable);	
				}
				
			}		  
		} catch (SQLException se) {
			System.out.println(se.getErrorCode() + ":" +se.getMessage());
			conn.rollback();
		}
		//conn.commit();
		//conn.setAutoCommit(true);
	}
	 
	private void upsertMap(Connection conn, TobeTable tobeTable, AsisTable asisTable) throws SQLException {
		String sqlStmt = "SELECT 'YES' FROM toas WHERE `toSystem` = ? and `toSchema` = ? and `toTable`=? and `asSystem` = ? and `asSchema` = ? and `asTable`=?";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1 ,tobeTable.getSystem     () );
		prepStmt.setString(2 ,tobeTable.getSchema     () );
		prepStmt.setString(3 ,tobeTable.getTable      () );
		prepStmt.setString(4 ,asisTable.getSystem     () );
		prepStmt.setString(5 ,asisTable.getSchema     () );
		prepStmt.setString(6 ,asisTable.getTable      () );
		ResultSet rs = prepStmt.executeQuery();
		
		if(rs.next()) {
		} else {
			String sql = "INSERT INTO `toas` VALUES (?,?,?,?,?,?)";
		    PreparedStatement stmt = conn.prepareStatement(sql);
		    stmt.setString(1 ,tobeTable.getSystem     () );
		    stmt.setString(2 ,tobeTable.getSchema     () );
		    stmt.setString(3 ,tobeTable.getTable      () );
		    stmt.setString(4 ,asisTable.getSystem     () );
		    stmt.setString(5 ,asisTable.getSchema     () );
		    stmt.setString(6 ,asisTable.getTable      () );
		    int result = stmt.executeUpdate();
		}
	}

	private int updateAsis(Connection conn, AsisTable asisTable) throws SQLException {
		//String sqlStmt = "SELECT `system`, `schema`, `table`, `apCode`, `comment`, `relation`, `cdVsTr`, `migRange`, `incCnt`, `incUnit`, `bizNonCmplt`, `cleanYn`, `cleanDueDt`, `cleanOwner`, `mapDefDueDt`, `fillupOwner`, `fillupDueDt`, `migSqlDueDt`, `migWay`, `ver` FROM `tobetable` WHERE  1 = 1 and `system` = ? and `schema` = ? and `table` = ? and ver = ?";
		String sqlStmt = "UPDATE `asistable` SET `system`=?, `schema`=?, `table`=?, `comment`=? WHERE `system` = ? and `schema` = ? and `table`=?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1 ,asisTable.getSystem     () );
		prepStmt.setString(2 ,asisTable.getSchema     () );
		prepStmt.setString(3 ,asisTable.getTable      () );
		prepStmt.setString(4 ,asisTable.getComment    () );
		prepStmt.setString(5 ,asisTable.getSystem());
		prepStmt.setString(6 ,asisTable.getSchema());
		prepStmt.setString(7 ,asisTable.getTable());
	    //stmt.setString(24,row.getTobeTable().getVersion());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		int result = prepStmt.executeUpdate();
		return result;
	}

	private int insertAsis(Connection conn, AsisTable asisTable) throws SQLException {
		String sql = "INSERT INTO `asistable` (`system`, `schema`, `table`, `comment`) VALUES (?,?,?,?)";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1 ,asisTable.getSystem     () );
	    stmt.setString(2 ,asisTable.getSchema     () );
	    stmt.setString(3 ,asisTable.getTable      () );
	    stmt.setString(4 ,asisTable.getComment    () );
	    int result = stmt.executeUpdate();
	    
		return result;
	}

	private int updateTobe(Connection conn, ListRow row) throws SQLException {
		//String sqlStmt = "SELECT `system`, `schema`, `table`, `apCode`, `comment`, `relation`, `cdVsTr`, `migRange`, `incCnt`, `incUnit`, `bizNonCmplt`, `cleanYn`, `cleanDueDt`, `cleanOwner`, `mapDefDueDt`, `fillupOwner`, `fillupDueDt`, `migSqlDueDt`, `migWay`, `ver` FROM `tobetable` WHERE  1 = 1 and `system` = ? and `schema` = ? and `table` = ? and ver = ?";
		String sqlStmt = "UPDATE `tobetable` SET `system`=?, `schema`=?, `table`=?, `apCode`=?, `comment`=?, `relation`=?, `cdVsTr`=?, `migRange`=?, `incCnt`=?, `incUnit`=?, `bizNonCmplt`=?, `cleanYn`=?, `cleanDueDt`=?, `cleanOwner`=?, `mapDefDueDt`=?, `fillupOwner`=?, `fillupDueDt`=?, `migSqlDueDt`=?, `migWay`=? WHERE `system` = ? and `schema` = ? and `table` = ?";;
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1 ,row.getTobeTable().getSystem     () );
		prepStmt.setString(2 ,row.getTobeTable().getSchema     () );
		prepStmt.setString(3 ,row.getTobeTable().getTable      () );
		prepStmt.setString(4 ,row.getTobeTable().getApCode     () );
		prepStmt.setString(5 ,row.getTobeTable().getComment    () );
		prepStmt.setString(6 ,row.getTobeTable().getRelation   () );
		prepStmt.setString(7 ,row.getTobeTable().getCdVsTr     () );
		prepStmt.setString(8 ,row.getTobeTable().getMigRange   () );
		prepStmt.setString(9 ,row.getTobeTable().getIncCnt     () );
		prepStmt.setString(10,row.getTobeTable().getIncUnit    () );
		prepStmt.setString(11,row.getTobeTable().getBizNonCmplt() );
		prepStmt.setString(12,row.getTobeTable().getCleanYn    () );
		prepStmt.setString(13,row.getTobeTable().getCleanDueDt () );
		prepStmt.setString(14,row.getTobeTable().getCleanOwner () );
		prepStmt.setString(15,row.getTobeTable().getMapDefDueDt() );
		prepStmt.setString(16,row.getTobeTable().getFillupOwner() );
		prepStmt.setString(17,row.getTobeTable().getFillupDueDt() );
		prepStmt.setString(18,row.getTobeTable().getMigSqlDueDt() );
		prepStmt.setString(19,row.getTobeTable().getMigWay     () );
	    //stmt.setString(20,row.getTobeTable().getVersion    () );
		prepStmt.setString(20,row.getTobeTable().getSystem());
		prepStmt.setString(21,row.getTobeTable().getSchema());
		prepStmt.setString(22,row.getTobeTable().getTable());
	    //stmt.setString(24,row.getTobeTable().getVersion());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		int result = prepStmt.executeUpdate();
		return result;
	}

	private int insertTobe(Connection conn, ListRow row) throws SQLException {

		String sql = "INSERT INTO `tobetable` (`system`, `schema`, `table`, `apCode`, `comment`, `relation`, `cdVsTr`, `migRange`, `incCnt`, `incUnit`, `bizNonCmplt`, `cleanYn`, `cleanDueDt`, `cleanOwner`, `mapDefDueDt`, `fillupOwner`, `fillupDueDt`, `migSqlDueDt`, `migWay`, `ver`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1 ,row.getTobeTable().getSystem     () );
	    stmt.setString(2 ,row.getTobeTable().getSchema     () );
	    stmt.setString(3 ,row.getTobeTable().getTable      () );
	    stmt.setString(4 ,row.getTobeTable().getApCode     () );
	    stmt.setString(5 ,row.getTobeTable().getComment    () );
	    stmt.setString(6 ,row.getTobeTable().getRelation   () );
	    stmt.setString(7 ,row.getTobeTable().getCdVsTr     () );
	    stmt.setString(8 ,row.getTobeTable().getMigRange   () );
	    stmt.setString(9 ,row.getTobeTable().getIncCnt     () );
	    stmt.setString(10,row.getTobeTable().getIncUnit    () );
	    stmt.setString(11,row.getTobeTable().getBizNonCmplt() );
	    stmt.setString(12,row.getTobeTable().getCleanYn    () );
	    stmt.setString(13,row.getTobeTable().getCleanDueDt () );
	    stmt.setString(14,row.getTobeTable().getCleanOwner () );
	    stmt.setString(15,row.getTobeTable().getMapDefDueDt() );
	    stmt.setString(16,row.getTobeTable().getFillupOwner() );
	    stmt.setString(17,row.getTobeTable().getFillupDueDt() );
	    stmt.setString(18,row.getTobeTable().getMigSqlDueDt() );
	    stmt.setString(19,row.getTobeTable().getMigWay     () );
	    stmt.setString(20,row.getTobeTable().getVersion    () );
	    int result = stmt.executeUpdate();
	    
		return result;
	}

	private AsisTable selectAsisFromDb(Connection conn, AsisTable asis) throws SQLException {
	    AsisTable asisData = null;

		//String sqlStmt = "SELECT * FROM employees where last_name=? and first_name like ?";
		String sqlStmt = "SELECT `system`, `schema`, `table`, `comment` FROM `asistable` WHERE  1 = 1 and `system` = ? and `schema` = ? and `table` = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, asis.getSystem());
		prepStmt.setString(2, asis.getSchema());
		prepStmt.setString(3, asis.getTable());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
	    if(rs.next()) {
	    	asisData = new AsisTable(rs);
	    } else {
	    	
	    }
	    rs.close();
	    prepStmt.close();
	    return asisData;
	}
*/
	public Hashtable<String, PogRow> selectPogList() throws SQLException, UnsupportedEncodingException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		getConn();
	    PogRow pog = null;
	    Hashtable<String, PogRow> pogList = new Hashtable<String, PogRow>();
		//String sqlStmt = "SELECT * FROM employees where last_name=? and first_name like ?";
		String sqlStmt = "SELECT `prodCd`, `nm`, `tl1`, `tl2`, `tl3`, `tl4` FROM `poglistcate`";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
	    while(rs.next()) {
	    	pog = new PogRow(rs);
	    	pogList.put(pog.getProdCode(), pog);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return pogList;
	}
	
	/**
	 * SELECT `tl1`, `tl2`, `prodCdlist`, `l3List` FROM `poggrp01` WHERE tl1 = ?
	 * @param lvl1
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public ArrayList<GrpResultSet> getGrpResultSet(String lvl1) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		ArrayList<GrpResultSet> grpResultSet = new ArrayList<GrpResultSet>();
		//String sqlStmt = "SELECT * FROM employees where last_name=? and first_name like ?";
		String sqlStmt = "SELECT `L1_CD` tl1, L2_CD tl2, `prodCdlist`, `l3List` FROM `prod_list5` WHERE tl1 = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, lvl1);
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		GrpResultSet grpResult;
	    while(rs.next()) {
	    	grpResult = new GrpResultSet(rs);
	    	grpResultSet.add(grpResult);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return grpResultSet;
	}

	/**
	 * SELECT `l1`, `l2`, `l3`, `l4`, `attrlist`, `at1`, `at2`, `at3`, `at4` FROM `selectedList` WHERE l1 = ? AND l2 = ?
	 * @param grpResult
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public Set<String> getSelectedList(GrpResultSet grpResult) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		Hashtable<String, String> attr = new Hashtable<String, String>(); 
		//String sqlStmt = "SELECT * FROM employees where last_name=? and first_name like ?";
		String sqlStmt = "SELECT `l1`, `l2`, `l3`, `l4`, `attrlist`, `at1`, `at2`, `at3`, `at4` FROM `selectedList` WHERE l1 = ? AND l2 = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, grpResult.getTl1());
		prepStmt.setString(2, grpResult.getTl2());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		AttrResultSet ar;
	    while(rs.next()) {
	    	ar = new AttrResultSet(rs);
	    	if(!"".equals(ar.getA1()) && ar.getA1() != null) attr.put(ar.getA1(),"A");
	    	if(!"".equals(ar.getA2()) && ar.getA2() != null) attr.put(ar.getA2(),"A");
	    	if(!"".equals(ar.getA3()) && ar.getA3() != null) attr.put(ar.getA3(),"A");
	    	if(!"".equals(ar.getA4()) && ar.getA4() != null) attr.put(ar.getA4(),"A");
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return attr.keySet();
	}

	/**
	 * SELECT * FROM `kbflist`
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public Hashtable<String, KbfAttrValueList> selectAttrValueSetList() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		Hashtable<String, KbfAttrValueList> attr = new Hashtable<String, KbfAttrValueList>(); 
		//String sqlStmt = "SELECT * FROM employees where last_name=? and first_name like ?";
		String sqlStmt = "SELECT * FROM `kbflist`";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		KbfAttrValueList kavl;
	    while(rs.next()) {
	    	kavl = new KbfAttrValueList(rs);
	    	attr.put(kavl.getLsl1()+":"+kavl.getLsl2()+":"+kavl.getLsl3()+":"+kavl.getAttr(), kavl);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return attr;
	}

	/**
	 * SELECT * FROM `pogkbfattrlist` WHERE prodCd = ?
	 * @param prodInfo
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public Hashtable<String, KbfAttrValueList> getAttrValList(ProdInfo prodInfo) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		Hashtable<String, KbfAttrValueList> attr = new Hashtable<String, KbfAttrValueList>(); 
		String sqlStmt = "SELECT * FROM `pogkbfattrlist` WHERE prodCd = ?";
		//System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		//System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, prodInfo.getCd());
		//System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		KbfAttrValueList kavl;
	    while(rs.next()) {
	    	kavl = new KbfAttrValueList(rs);
	    	attr.put(kavl.getAttr(), kavl);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return attr;
		
	}

	public void getAttrList(ProdInfo prodInfo) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<ProdInfo2> getMoreGrpResult(GrpResultSet grpResult) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		ArrayList<ProdInfo2> listProdInfo = new ArrayList<ProdInfo2>();
		//String sqlStmt = "SELECT * FROM employees where last_name=? and first_name like ?";
		String sqlStmt = "SELECT * FROM `poglistcate` WHERE prodCd IN ("+grpResult.getInStr()+") ORDER BY tl3";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		GrpResultSet grpResultSet;
		ProdInfo2 prodInfo2;
	    while(rs.next()) {
	    	prodInfo2 = new ProdInfo2(rs);
	    	listProdInfo.add(prodInfo2);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return listProdInfo;
	}

	public Hashtable<String, PogRed> getPogRed() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		Hashtable<String,PogRed> pogRedList = new Hashtable<String,PogRed>();
		String sqlStmt = "SELECT * FROM `pog_red`";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		GrpResultSet grpResultSet;
		PogRed pogRed;
	    while(rs.next()) {
	    	pogRed = new PogRed(rs);
	    	pogRedList.put(pogRed.getProd_cd(),pogRed);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return pogRedList;
	}

	public static void insertKbfData(String l3, String prodCd, String prodNm,
			String value, String ts) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		
		String connectionUrl = "jdbc:mysql://localhost:3306/usmig?useUnicode=yes&characterEncoding=UTF-8";
		String connectionUser = "root";
		String connectionPassword = "apmsetup";

		Class.forName(JDBC_DRIVER).newInstance();
		Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
		String sqlStmt = "INSERT INTO group.cg_prod_kbf VALUES (?,?,?,?,?,?,?,?,'Y','USMIG',null,'USMIG',null)";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1, l3);
		prepStmt.setString(2, prodCd);
		prepStmt.setString(3, prodNm);
		prepStmt.setString(4, "");
		prepStmt.setString(5, value);
		prepStmt.setString(6, "");
		prepStmt.setString(7, ts);
		prepStmt.setString(8, "Y");
		int result = prepStmt.executeUpdate();
		//System.out.println("Insert result : "+result);
		prepStmt.close();
		conn.close();
	}
	
	public static void insertSql() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String connectionUrl = "jdbc:mysql://localhost:3306/usmig?useUnicode=yes&characterEncoding=UTF-8";
		String connectionUser = "root";
		String connectionPassword = "apmsetup";

		Class.forName(JDBC_DRIVER).newInstance();
		Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
		File file = new File("D:\\work\\1119.sql");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String t;
		PreparedStatement prepStmt;
		while( (t = br.readLine())!= null) {
			prepStmt = conn.prepareStatement(t);
			int result = prepStmt.executeUpdate();
			prepStmt.close();
		}
		conn.close();
	}

	/**
	 * Level2 List
	 * 
	 * @param substring
	 * @return
	 * @throws SQLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public Hashtable<String,PogRed> getLevel2List(String lvl1) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		Hashtable<String,PogRed> pogRedList = new Hashtable<String,PogRed>();
		String sqlStmt = "SELECT * FROM `cg_cat`";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		//System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		//System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		GrpResultSet grpResultSet;
		PogRed pogRed;
	    while(rs.next()) {
	    	pogRed = new PogRed(rs);
	    	pogRedList.put(pogRed.getProd_cd(),pogRed);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return pogRedList;
	}

	/**
	 * SELECT * FROM `prod_list5`
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public Hashtable<String, Prod> getProdList() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		Hashtable<String,Prod> prodList = new Hashtable<String,Prod>();
		String sqlStmt = "SELECT * FROM `prod_list5`";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		//System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		//System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		GrpResultSet grpResultSet;
		Prod prod;
	    while(rs.next()) {
	    	prod = new Prod(rs);
	    	prodList.put(prod.getPROD_CD(),prod);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return prodList;
	}
	/**
	 * SELECT * FROM `prod_list5`
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public ArrayList<Prod2> getProdList2(String l1Cd, String l2Cd) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		ArrayList<Prod2> prodList = new ArrayList<Prod2>();
		String sqlStmt = 
				" SELECT L1_CD, l1_nm, L2_CD, l2_nm, pl.L3_CD, L3_NM, SORT_SEQ, L4_CD, L4_NM, PROD_CD, PROD_NM, PROD_SHRT_NM, PROD_OPER_STAT_CD, STAT, KBF_ATTR_ID, KBF_ATTR_NM, KBF_SHRT_NM, KBF_TYP_CD, GRP_USE_YN, l3k.USE_YN " +
				" FROM prod_list5 pl, cg_kbf_l3 l3k " +
				" WHERE pl.l3_cd = l3k.l3_cd " +
				" AND l1_cd = ?" +
				" AND l2_cd = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1, l1Cd);
		prepStmt.setString(2, l2Cd);
		System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
	    while(rs.next()) {
	    	prodList.add(new Prod2(rs));
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return prodList;
	}

	/**
	 * SELECT * FROM `prod_list5` WHERE L1_CD = ?
	 * 
	 * @param substring
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public Hashtable<String,Prod> getL2ProdList(String substring) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		getConn();
		Hashtable<String,Prod> prodList = new Hashtable<String,Prod>();
		String sqlStmt = "SELECT * FROM `prod_list5` WHERE L1_CD = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		//System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, substring);
		//System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		Prod prod;
	    while(rs.next()) {
	    	prod = new Prod(rs);
	    	prodList.put(prod.getPROD_CD(),prod);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return prodList;
	}

	/**
	 * SELECT distinct L2_NM , L2_CD FROM `prod_list5` WHERE L1_CD = ?
	 * 
	 * @param substring
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public Hashtable<String,String> getL2List(String substring) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		getConn();
		Hashtable<String,String> l2List = new Hashtable<String,String>();
		String sqlStmt = "SELECT distinct L2_NM , L2_CD FROM `prod_list5` WHERE L1_CD = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		//System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, substring);
		//System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
	    while(rs.next()) {
	    	l2List.put(rs.getString("L2_CD"), rs.getString("L2_NM"));
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return l2List;
	}

	/**
	 * 중분류내의 KBF
	 * @param l2
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public ArrayList<KbfAttr> getKbfList(String l2) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		getConn();
		Hashtable<String,Prod> prodList = new Hashtable<String,Prod>();
		String sqlStmt = "SELECT a.L1_CD, a.L2_CD, b.L3_CD, a.CAT_NM, KBF_ATTR_ID, KBF_ATTR_NM, KBF_SHRT_NM, KBF_TYP_CD, GRP_USE_YN, b.USE_YN FROM ( SELECT * FROM cg_cat WHERE `L2_CD` = ? AND `LVL_VAL` = '3' ORDER BY SORT_SEQ )a, cg_kbf_l3 b WHERE a.cat_cd = b.l3_cd";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		//System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, l2);
		//System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<KbfAttr> kbfList = new ArrayList<KbfAttr>();
	    while(rs.next()) {
	    	kbfList.add(new KbfAttr(rs));
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return kbfList;
	}

	/**
	 * SELECT KBF_ATTR_ID, KBF_ATTR_VAL_ID, KBF_ATTR_VAL_NM, ETC_ATTR_VAL_YN, GRP_SORT_SEQ, SORT_SEQ, GRP_USE_YN FROM cg_kbf_val WHERE KBF_ATTR_ID = ? ORDER BY kbf_attr_id, SORT_SEQ
	 * 
	 * @param headerAttrCodeList
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public Hashtable<String, ArrayList<KbfAttrList>> getKbfAtrrList(ArrayList<String[]> headerAttrCodeList) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		getConn();
		Hashtable<String,ArrayList<KbfAttrList>> prodList = new Hashtable<String,ArrayList<KbfAttrList>>();
		String sqlStmt = "SELECT KBF_ATTR_ID, KBF_ATTR_VAL_ID, KBF_ATTR_VAL_NM, ETC_ATTR_VAL_YN, GRP_SORT_SEQ, SORT_SEQ, GRP_USE_YN FROM cg_kbf_val WHERE KBF_ATTR_ID = ? ORDER BY kbf_attr_id, SORT_SEQ";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		ResultSet rs = null;
		KbfAttrList kbfAttrList;
		for(String[] kbfId : headerAttrCodeList) {
			ArrayList<KbfAttrList> arrayOfKbfAttrList = new ArrayList<KbfAttrList>();
			prepStmt.setString(1, kbfId[0]);
			rs = prepStmt.executeQuery();
			ArrayList<String[]> KbfAttrList = new ArrayList<String[]>();
		    while(rs.next()) {
		    	arrayOfKbfAttrList.add(new KbfAttrList(rs));
		    }
		    rs.close();
		    prodList.put(kbfId[0], arrayOfKbfAttrList);
		}
		
	    prepStmt.close();
	    closeConn();
	    return prodList;
	}

	/**
	 * SELECT * FROM `prod_list5` WHERE L2_CD = ?
	 * 
	 * @param l2Cd
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public Hashtable<String,Prod> getProdListL2(String l2Cd) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		Hashtable<String,Prod> prodList = new Hashtable<String,Prod>();
		String sqlStmt = "SELECT * FROM `prod_list5` WHERE L2_CD = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		//System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, l2Cd);
		//System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		Prod prod;
	    while(rs.next()) {
	    	prod = new Prod(rs);
	    	prodList.put(prod.getPROD_CD(),prod);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return prodList;
	}

	/**
	 * SELECT * FROM `prod_list5` WHERE L2_CD = ?
	 * 
	 * @param l2Cd
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public ArrayList<Prod> getProdOrderListL2(String l2Cd) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		ArrayList<Prod> prodList = new ArrayList<Prod>();
		String sqlStmt = "SELECT * FROM `prod_list5` WHERE L2_CD = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		//System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, l2Cd);
		//System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		Prod prod;
	    while(rs.next()) {
	    	prodList.add(new Prod(rs));
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return prodList;
	}

	/**
	 * SELECT * FROM `prod_list5` WHERE L2_CD = ?
	 * 
	 * @param l2Cd
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public ArrayList<String> getOrderedProdListL2(String l2Cd) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		ArrayList<String> prodList = new ArrayList<String>();
		String sqlStmt = "SELECT * FROM `prod_list5` WHERE L2_CD = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		//System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, l2Cd);
		//System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
	    while(rs.next()) {
	    	prodList.add(rs.getString("PROD_CD"));
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return prodList;
	}

	public ArrayList<String[]> getAttrCodeList(String l2Cd) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		ArrayList<String[]> prodList = new ArrayList<String[]>();
		String sqlStmt = "select distinct kbf_attr_id, kbf_attr_nm from ( SELECT * FROM `prod_list5` WHERE L2_CD = ? ) a, cg_kbf_l3 b where a.l3_cd = b.l3_cd order by kbf_attr_id";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		//System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		prepStmt.setString(1, l2Cd);
		//System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		String[] a;
	    while(rs.next()) {
	    	a = new String[2];
	    	a[0] = rs.getString("kbf_attr_id");
	    	a[1] = rs.getString("kbf_attr_nm");
	    	prodList.add(a);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return prodList;
	}

	public Hashtable<String, ArrayList<KbfAttrValueList2>> selectKbfAttrValueSetList() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		Hashtable<String, ArrayList<KbfAttrValueList2>> kbfAttrValueSetList = new Hashtable<String, ArrayList<KbfAttrValueList2>>();
		String sqlStmt = "SELECT DISTINCT KBF_ATTR_ID FROM `cg_kbf_val`";
		String sqlStmt2 = "SELECT * FROM `cg_kbf_val` WHERE KBF_ATTR_ID = ?";
		System.out.println("SQL Statement:\n\t" + sqlStmt);
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		PreparedStatement prepStmt2 = conn.prepareStatement(sqlStmt2);
		//System.out.println("Prepared Statement before bind variables set:\n\t" + prepStmt.toString());
		//System.out.println("Prepared Statement after bind variables set:\n\t" + prepStmt.toString());
		ResultSet rs = prepStmt.executeQuery();
		ResultSet rs2;
		KbfAttrValueList2 kavl;
		ArrayList<KbfAttrValueList2> al;
		String kbfAttrId;
	    while(rs.next()) {
	    	kbfAttrId = rs.getString("KBF_ATTR_ID");
	    	prepStmt2.setString(1, kbfAttrId);
	    	rs2 = prepStmt2.executeQuery();
	    	al = new ArrayList<KbfAttrValueList2>();
	    	while(rs2.next()) {
	    		kavl = new KbfAttrValueList2(rs2);
	    		al.add(kavl);
	    	}
	    	rs2.close();
	    	kbfAttrValueSetList.put(kbfAttrId, al);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return kbfAttrValueSetList;
	}

	public Hashtable<String, SelectedData> selectOldKbf() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {	
		getConn();
		Hashtable<String, SelectedData> selectedData = new Hashtable<String, SelectedData>();
		String sqlStmt = "SELECT * FROM oldkbf";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		ResultSet rs = prepStmt.executeQuery();
		SelectedData sd;
	    while(rs.next()) {
	    	sd = new SelectedData(rs);
	    	selectedData.put(sd.getProd_cd()+":"+sd.getKbfAttrNm(), sd);
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return selectedData;
	}

	public String[] getWonsanji() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		String[] t = new String[57];
		ArrayList<String> ta = new ArrayList<String>();
		String sqlStmt = "SELECT * FROM wonsanji";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		ResultSet rs = prepStmt.executeQuery();
	    while(rs.next()) {
	    	ta.add(rs.getString("UDV_2_VAL"));
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return ta.toArray(t);
	}

	public String getL2Code(String l1Code, String sheetName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		getConn();
		String sqlStmt = "SELECT * FROM cg_cat WHERE l1_cd = ? and CAT_NM = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1, l1Code);
		prepStmt.setString(2, sheetName);
		ResultSet rs = prepStmt.executeQuery();
		String catCd = null;
	    while(rs.next()) {
	    	catCd = rs.getNString("CAT_CD");
	    }
	    rs.close();
	    prepStmt.close();
	    closeConn();
	    return catCd;
	}

	public String getL3Cd(String l1Code,String l2Code,String stringCellValue) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		//getConn();
		String sqlStmt = "SELECT * FROM cg_cat WHERE l1_cd = ? and l2_cd = ? and CAT_NM = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1, l1Code);
		prepStmt.setString(2, l2Code);
		prepStmt.setString(3, stringCellValue);
		ResultSet rs = prepStmt.executeQuery();
		String catCd = null;
	    while(rs.next()) {
	    	if(rs.getString("CAT_CD").length() == 5)
	    		catCd = rs.getString("CAT_CD");
	    }
	    rs.close();
	    prepStmt.close();
	    //closeConn();
	    return catCd;
	}

	public String getKbfAttrCode(String l3Cd, String kbfAttrNm)  throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		//getConn();
		String sqlStmt = "SELECT * FROM cg_kbf_l3 WHERE l3_cd = ? and kbf_attr_nm = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1, l3Cd);
		prepStmt.setString(2, kbfAttrNm);
		ResultSet rs = prepStmt.executeQuery();
		String catCd = null;
	    while(rs.next()) {
	    	catCd = rs.getNString("KBF_ATTR_ID");
	    }
	    rs.close();
	    prepStmt.close();
	    //closeConn();
	    return catCd;
	}

	public String getkbfAttrValCd(String kbfAttrCd, String kbfAttrValNm)   throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		//getConn();
		String sqlStmt = "SELECT * FROM cg_kbf_val WHERE KBF_ATTR_ID = ? and KBF_ATTR_VAL_NM = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1, kbfAttrCd);
		prepStmt.setString(2, kbfAttrValNm);
		ResultSet rs = prepStmt.executeQuery();
		String catCd = null;
	    while(rs.next()) {
	    	catCd = rs.getNString("KBF_ATTR_VAL_ID");
	    }
	    rs.close();
	    prepStmt.close();
	    //closeConn();
	    return catCd;
	}

	public String getProdCd(String catNm, String prodNm)   throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		//getConn();
		String sqlStmt = "SELECT * FROM prod_list WHERE PROD_NM = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1, prodNm);
		ResultSet rs = prepStmt.executeQuery();
		String catCd = "";
	    while(rs.next()) {
	    	catCd = rs.getNString("PROD_CD");
	    }
	    rs.close();
	    prepStmt.close();
	    //closeConn();
	    return catCd;
	}

	public int insertKbf(Kbf kbf) throws SQLException {
		String sql = "INSERT INTO aggriresult VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1 ,kbf.getL1Cd());
	    stmt.setString(2 ,kbf.getL2Cd());
	    stmt.setString(3 ,kbf.getL3Cd());
	    stmt.setString(4 ,kbf.getProdCd());
	    stmt.setString(5 ,kbf.getProdNm());
	    stmt.setString(6 ,kbf.getKbfAttrNm()   !=null ? kbf.getKbfAttrNm()    : "");
	    stmt.setString(7 ,kbf.getKbfAttrCd()   !=null ? kbf.getKbfAttrCd()    : "");
	    stmt.setString(8 ,kbf.getKbfAttrVal()  !=null ? kbf.getKbfAttrVal()   : "");
	    stmt.setString(9 ,kbf.getKbfAttrValNm()!=null ? kbf.getKbfAttrValNm() : "");
	    stmt.setString(10,kbf.getDanCd());
	    stmt.setString(11,kbf.getChamgo()      !=null ? kbf.getChamgo()       : "");
	    stmt.setString(12,kbf.getNoExist()     !=null ? kbf.getNoExist()      : "");
	    stmt.setString(13,"");
	    int result = stmt.executeUpdate();
	    
		return result;
		
	}

	public String getkbfAttrValCdWonsanzi(String kbfAttrCd, String kbfAttrValNm) throws SQLException {
		//getConn();
		String sqlStmt = "SELECT DTL_CD FROM  wonsanji WHERE DTL_CD_NM  = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1, kbfAttrValNm);
		ResultSet rs = prepStmt.executeQuery();
		String catCd = null;
	    if(rs.next()) {
	    	catCd = rs.getNString("DTL_CD");
	    }
	    rs.close();
	    prepStmt.close();
	    //closeConn();
	    return catCd;
	}

	public String getKbfDanwiCode(String str) throws SQLException {
		//getConn();
		String sqlStmt = "SELECT danCd FROM  danwui WHERE danNm  = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sqlStmt);
		prepStmt.setString(1, str);
		ResultSet rs = prepStmt.executeQuery();
		String catCd = null;
	    if(rs.next()) {
	    	catCd = rs.getNString("danCd");
	    }
	    rs.close();
	    prepStmt.close();
	    //closeConn();
	    return catCd;
	}
	
}
