package com.bigdata.spitter.db;

import java.io.File;
import java.io.Serializable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockConflictException;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.TransactionConfig;

/**
 * Berkeley DB 基础类
 * 
 * @author Cang
 *
 * @param <K>
 * @param <V>
 */
public class BDBase<K, V extends Serializable> {
    // 数据库根目录
    private static String envHomePath = "/tmp/berkeleydata";
    // 数据库环境
    private static Environment dbEnv = null;
    // 类目录
    private static String CLASS_CATALOG = "java_class_catalog";
    // 数据库对象
    private Database database = null;
    private static Database catalogDatabase = null;
    // 存储的类目录
    private static StoredClassCatalog javaCatalog;
    // 数据库名称
    private String dbName = null;

    // 实际数据库的映射
    private StoredMap<K, V> storedMapDB = null;

    static {
	// 数据库环境配置
	EnvironmentConfig envConfig = new EnvironmentConfig();
	// 允许自动创建数据库环境
	envConfig.setAllowCreate(true);
	// 设置启动事务
	envConfig.setTransactional(true);
	envConfig.setReadOnly(false);
	envConfig.setTxnTimeout(10000, TimeUnit.MILLISECONDS);
	envConfig.setLockTimeout(10000, TimeUnit.MILLISECONDS);
	// 获取数据库环境根目录
	File file = new File(envHomePath);
	if (!file.exists()) {
	    file.mkdirs();
	}
	dbEnv = new Environment(file, envConfig);

	// 类目录数据库配置
	DatabaseConfig classCatalogdbConfig = new DatabaseConfig();
	classCatalogdbConfig.setTransactional(true);
	classCatalogdbConfig.setAllowCreate(true);
	// 打开类目录数据库
	catalogDatabase = dbEnv.openDatabase(null, CLASS_CATALOG, classCatalogdbConfig);
	// 打开存储的类目录
	javaCatalog = new StoredClassCatalog(catalogDatabase);
    }

    public BDBase(String name) {
	this.dbName = name;
	// 实际使用数据库配置
	DatabaseConfig dbConfig = new DatabaseConfig();
	dbConfig.setTransactional(true);
	dbConfig.setAllowCreate(true);
	// 打开数据库
	database = dbEnv.openDatabase(null, dbName, dbConfig);
    }

    public void binding(Class<K> keyClass, Class<V> valueClass) {
	EntryBinding<K> keyBinding = new SerialBinding<K>(javaCatalog, keyClass);
	EntryBinding<V> valueBinding = new SerialBinding<V>(javaCatalog, valueClass);
	storedMapDB = new StoredMap<K, V>(database, keyBinding, valueBinding, true);
    }

    public synchronized boolean containsKey(K key) {
	return storedMapDB.containsKey(key);
    }

    /**
     * 向数据库中添加数据
     * 
     * @param key
     * @param value
     * @return
     */
    public synchronized boolean put(K key, V value) {
	storedMapDB.put(key, value);
	return true;
    }

    /**
     * 获取下一条数据
     * 
     * @return
     */
    public synchronized V getNext() {
	V v = null;
	if (!storedMapDB.isEmpty()) {
	    Entry<K, V> entry = null;
	    Set<Entry<K, V>> entrys = storedMapDB.entrySet();
	    entry = entrys.iterator().next();
	    v = entry.getValue();
	    delete(entry.getKey()); // 删除当前记录
	    return v;
	} else {
	    return null;
	}
    }

    /**
     * 获取数据库的大小
     * 
     * @return
     */
    public int size() {
	return storedMapDB.size();
    }

    /**
     * 判断数据是否为空
     * 
     * @return
     */
    public synchronized boolean isEmpty() {
	return storedMapDB.isEmpty();
    }

    public void delete(K key) {
	storedMapDB.remove(key);
    }

    public void close() {
	if (!storedMapDB.isEmpty()) {
	    storedMapDB.clear();
	}
    }

    /*
     * 关闭当前数据库
     */
    public void closeAll() {
	if (database != null) {
	    database.close();
	}
	if (dbEnv != null) {
	    dbEnv.cleanLog();
	    dbEnv.close();
	}
    }
}
