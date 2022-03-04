package jp.learningpark.framework.gakkenID.dao;

import jp.learningpark.framework.gakkenID.dto.GakkenId;
import jp.learningpark.framework.gakkenID.dto.GakkenTransactionID;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GakkenApiDao {

	/**
	 * 学研ID構造体を取得する
	 * 
	 * @param gakkenId 学研ID構造体
	 * @return　学研ID構造体
	 */
	public List<GakkenId> selectGakkenId(GakkenId gakkenId);

	/**
	 * 学研ID構造体をバッチ取得する
	 * @param gakkenIdList
	 * @return
	 */
	public List<GakkenId> selectGakkenIdList(@Param("gakkenIdList") List<String> gakkenIdList);
	/**
	 * 学研ID構造体を新規する
	 * 
	 * @param gakkenId 学研ID構造体
	 * @return　件数
	 */
	public int insertGakkenId(GakkenId gakkenId);

	/**
	 * 学研TID構造体を取得する
	 * 
	 * @param gakkenTID 学研TID構造体
	 * @return 学研TID構造体
	 */
	public List<GakkenTransactionID> selectGakkenTID(GakkenTransactionID gakkenTID);
	
	/**
	 * 学研ID構造体を更新する
	 * 
	 * @param gakkenId 学研ID構造体
	 * @return　件数
	 */
	public int updateGakkenId(GakkenId gakkenId);

	/**
	 * 学研ID構造体を新規する(GakkenTransaction)
	 *
	 * @param gakkenTransactionID 学研TID構造体
	 * @return　件数
	 */
	public int insertGakkenTransaction(GakkenTransactionID gakkenTransactionID);

	/**
	 * 学研TID構造体を取得する
	 *
	 * @param gidpks 学研gidpks
	 * @param gpidpks 学研gpidpks
	 * @param gtidpks  学研gtidpks
	 * @return 学研TID構造体
	 */
	public List<GakkenTransactionID> selectGakkenTIDOfArray(@Param("gidpks") String[] gidpks, @Param("gpidpks") String[] gpidpks, @Param("gtidpks") String[] gtidpks);
}
