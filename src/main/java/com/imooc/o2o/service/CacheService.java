package com.imooc.o2o.service;

public interface CacheService {
	/**
	 * 依据key前缀删除匹配该模式下的所有key-value
	 * 如传入shopcategory 则以shopcategory打头的shopcategory_xxx等都会被清空
	 * @param keyPrefix
	 */
	void removeFromCache(String keyPrefix);
}
