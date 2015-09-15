package kr.pe.usee.db.mysql;

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

import kr.pe.usee.excel.AttrResultSet;
import kr.pe.usee.excel.GrpResultSet;
import kr.pe.usee.excel.KbfAttrValueList;
import kr.pe.usee.excel.PogRed;
import kr.pe.usee.excel.PogRow;
import kr.pe.usee.excel.ProdInfo;

public class MysqlDb {

	
	// JDBC driver name and database URL
 	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
  
	Connection conn = null;

	String connectionUrl = "jdbc:mysql://localhost:3306/usmig?useUnicode=yes&characterEncoding=UTF-8";
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
		String sqlStmt = "SELECT `tl1`, `tl2`, `prodCdlist`, `l3List` FROM `poggrp01` WHERE tl1 = ?";
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
	
	
}
