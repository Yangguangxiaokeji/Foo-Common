package com.foogui.foo.common.redis.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foogui.foo.common.core.utils.NumberBoxUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisObjectUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    // =============================1-common============================
    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return 设定缓存失效时间成功或失败
     */
    public boolean expire(@Nonnull String key, long time) {
        return NumberBoxUtil.unboxBoolean(redisTemplate.expire(key, time, TimeUnit.SECONDS));
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(@Nonnull String key) {
        return NumberBoxUtil.unboxLong(redisTemplate.getExpire(key, TimeUnit.SECONDS));
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        return NumberBoxUtil.unboxBoolean(redisTemplate.hasKey(key));
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    // ============================2-String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    // 获取java对象类型
    public <T> T get(String key, Class<T> clazz) {
        Object str = get(key);
        if (str instanceof String) {
            JSONObject jsonObject = JSON.parseObject((String) str);
            return JSON.toJavaObject(jsonObject, clazz);
        }
        throw new IllegalStateException(str.getClass()+" is not "+clazz);
    }


    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param timeout  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long timeout,TimeUnit duration) {
        try {
            redisTemplate.opsForValue().set(key, value,timeout,duration);
            return true;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 递增 适用场景： 高并发生成订单号，秒杀类的业务逻辑等。。
     *
     * @param key   键
     * @param delta 增加数量(大于0)
     * @return 增加数量
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new IllegalStateException("递增因子必须大于0");
        }
        return NumberBoxUtil.unboxLong(redisTemplate.opsForValue().increment(key, delta));
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 减少数量(小于0)
     * @return 减少数量
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new IllegalStateException("递减因子必须大于0");
        }
        return NumberBoxUtil.unboxLong(redisTemplate.opsForValue().increment(key, -delta));
    }

    // ================================3-Map=================================

    /**
     * 获取HashGet中item键的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 向HashSet中放置多个键值对
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hSetMap(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 向HashSet中放置多个键值对并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hSetMap(String key, Map<String, Object> map, long time) {
        try {
            if (hSetMap(key, map) && time > 0) {
                return expire(key, time);
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建,对并设置时间
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value, long time) {
        try {
            if (hSet(key, item, value) && time > 0) {
                return expire(key, time);
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以为多个,不能为null
     */
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   增加数量(大于0)
     * @return 增加后的值返回
     */
    public double hIncr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   减少数量(小于0)
     * @return 减少后的值返回
     */
    public double hDecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================4-set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return Set中的所有值
     */

    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error(key, e);
            return Collections.emptySet();
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
          
            return NumberBoxUtil.unboxBoolean(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return NumberBoxUtil.unboxLong(redisTemplate.opsForSet().add(key, values));
        } catch (Exception e) {
            log.error(key, e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            long count = sSet(key, time);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error(key, e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return set缓存的长度
     */
    public long sSetSize(String key) {
        try {
            return NumberBoxUtil.unboxLong(redisTemplate.opsForSet().size(key));
        } catch (Exception e) {
            log.error(key, e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            return NumberBoxUtil.unboxLong(redisTemplate.opsForSet().remove(key, values));
        } catch (Exception e) {
            log.error(key, e);
            return 0;
        }
    }

    // ============================5-zSet=============================

    /**
     * 根据key获取zSet中的所有值
     *
     * @param key 键
     * @return zSet中的所有值
     */
    public Set<Object> zSGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error(key, e);
            return Collections.emptySet();
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean zSHasKey(String key, Object value) {
        try {
            return NumberBoxUtil.unboxBoolean(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    public Boolean zSSet(String key, Object value, double score) {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long zSSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return NumberBoxUtil.unboxLong(count);
        } catch (Exception e) {
            log.error(key, e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return set缓存的长度
     */
    public long zSGetSetSize(String key) {
        try {
            return NumberBoxUtil.unboxLong(redisTemplate.opsForSet().size(key));
        } catch (Exception e) {
            log.error(key, e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long zSetRemove(String key, Object... values) {
        try {
            return NumberBoxUtil.unboxLong(redisTemplate.opsForSet().remove(key, values));
        } catch (Exception e) {
            log.error(key, e);
            return 0;
        }
    }
    // ===============================6-list=================================

    public <T> List<T> lGet(String key) {
        return lGet(key, 0, -1);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始 0 是第一个元素
     * @param end   结束 -1代表所有值
     * @return 取出来的元素 总数 end-start+1
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> lGet(String key, long start, long end) {
        try {
            return (List<T>) redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error(key, e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return list缓存的长度
     */
    public long lGetListSize(String key) {
        try {
            return NumberBoxUtil.unboxLong(redisTemplate.opsForList().size(key));
        } catch (Exception e) {
            log.error(key, e);
            return 0;
        }
    }

    /**
     * 通过索引获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 获取list中index的值
     */
    @SuppressWarnings("unchecked")
    public <T> T lGetIndex(String key, long index) {
        try {
            return (T) redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error(key, e);
            return null;
        }
    }

    /**
     * 将值放入list
     *
     * @param key   键
     * @param value 值
     * @return true 成功 false 失败
     */
    public <T> boolean lSet(String key, T value) {
        try {
            return NumberBoxUtil.unboxLong(redisTemplate.opsForList().rightPush(key, value)) > 0;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 将值放入list
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return true 成功 false 失败
     */
    public <T> boolean lSet(String key, T value, long time) {
        try {
            if (lSet(key, value) && time > 0) {
                return expire(key, time);
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return true 成功 false 失败
     */
    public <T> boolean lSetList(String key, List<T> value) {
        try {
            return NumberBoxUtil.unboxLong(redisTemplate.opsForList().rightPushAll(key, value)) > 0;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return true 成功 false 失败
     */
    public boolean lSetList(String key, List<Object> value, long time) {
        try {
            if (lSetList(key, value) && time > 0) {
                return expire(key, time);
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return true 成功 false 失败
     */
    public <T> boolean lUpdateIndex(String key, long index, T value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 根据key修改vulue中的某条数据
     *
     * @param key   键
     * @param value 值
     * @return true 成功 false 失败
     */
    public <T> boolean lUpdateByValue(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, value, 0);
            return true;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            return NumberBoxUtil.unboxLong(redisTemplate.opsForList().remove(key, count, value));
        } catch (Exception e) {
            log.error(key, e);
            return 0;
        }
    }

    // ===============================6-lock=================================

    private static final String LOCK_PREFIX = "REDIS_LOCK_";

    /**
     * 设置分布式锁
     *
     * @param key 锁键
     * @return 是否成功上锁
     */
    public boolean lock(String key, long expire) {
        String lockKey = LOCK_PREFIX + key;
        return NumberBoxUtil.unboxBoolean((Boolean) redisTemplate.execute((RedisCallback<Object>) connection -> {
            long expireAt = System.currentTimeMillis() + expire + 1;
            Boolean acquire = connection.setNX(lockKey.getBytes(), String.valueOf(expireAt).getBytes());
            if (NumberBoxUtil.unboxBoolean(acquire)) {
                return true;
            } else {
                byte[] value = connection.get(lockKey.getBytes());
                if (value != null && value.length > 0) {
                    long expireTime = Long.parseLong(new String(value));
                    if (expireTime < System.currentTimeMillis()) {
                        byte[] oldValue = connection.getSet(lockKey.getBytes(), String.valueOf(expireAt).getBytes());
                        return oldValue != null && Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        }));
    }

    /**
     * 取消分布式锁
     *
     * @param key 锁键
     */
    public void unlock(String key) {
        redisTemplate.delete(LOCK_PREFIX + key);
    }
}
