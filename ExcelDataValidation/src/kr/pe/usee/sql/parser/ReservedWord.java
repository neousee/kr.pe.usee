package kr.pe.usee.sql.parser;

import java.io.Serializable;

public interface ReservedWord extends Serializable {
	public static final String KWD_SELECT      = "SELECT"    ;
	public static final String KWD_INSERT      = "INSERT"    ;
	public static final String KWD_COMMIT      = "COMMIT"    ;
	public static final String KWD_FROM        = "FROM"      ;
	public static final String KWD_GROUP_BY    = "GROUP_BY"  ;
	public static final String KWD_UNION       = "UNION"     ;
	public static final String KWD_MINUS       = "MINUS"     ;
	public static final String KWD_EXCEPT      = "EXCEPT"    ;
	public static final String KWD_INTERSECT   = "INTERSECT" ;
	public static final String KWD_LIMIT       = "LIMIT"     ;
	public static final String KWD_FOR_UPDATE  = "FOR UPDATE";
	public static final Object KWD_WHERE       = "WHERE"     ;
	public static final String LEFT_PARA       = "(";
		

}
