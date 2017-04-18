package cn.sowell.copframe.common;

import java.io.Serializable;

/**
 * 
 * <p>Title: UserIdentifier</p>
 * <p>Description: </p><p>
 * 用户信息接口
 * </p>
 * @author Copperfield Zhang
 * @date 2017年4月11日 上午11:18:21
 */
public interface UserIdentifier {
	Serializable getId();
	String getNickname();
}
