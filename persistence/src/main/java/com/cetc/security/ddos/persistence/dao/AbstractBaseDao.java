package com.cetc.security.ddos.persistence.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@SuppressWarnings("all")
public abstract class AbstractBaseDao<T> {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	protected Class entityClass = null;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected Class getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) this.getSuperClassGenricType(getClass(), 0);
		}
		return entityClass;
	}
	
	public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {  
		  
        Type genType = clazz.getGenericSuperclass();  
  
        if (!(genType instanceof ParameterizedType)) {  
            return Object.class;  
        }  
  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
  
        if (index >= params.length || index < 0) {  
            return Object.class;  
        }  
        if (!(params[index] instanceof Class)) {  
            return Object.class;  
        }  
        return (Class) params[index];  
    }

	public AbstractBaseDao() {
		super();
		entityClass = getEntityClass();
	}

	@Transactional
	public void insert(T o) {
		entityManager.persist(o);
		entityManager.flush();
	};

    public void insertNoTrans(T o) {
        entityManager.persist(o);
        //entityManager.flush();
    }

    public void deleteNoTrans(T o) {
        entityManager.remove(o);
    }

	@Transactional
	public void delete(T o) {
        deleteNoTrans(o);
		entityManager.flush();
	};

	public T get(Serializable id) {
		return (T) entityManager.find(entityClass, id);
	}

	@Transactional
	public void update(T o) {
        entityManager.merge(o);
        entityManager.flush();
    };

    public void updateNoTrans(T o) {
        entityManager.merge(o);
        // entityManager.flush();
    };

    public int updateByPropertiesNoTrans(Map<String, Object> params, Map<String, Object> where) {
        String jpql = " update " + entityClass.getSimpleName() + " o ";
        if ((params != null) && (params.size() > 0)) {
            jpql += "set ";
            int index = 0;
            for (String property : params.keySet()) {
                if (index > 0) {
                    jpql += ", o." + property + "=:" + property;
                } else {
                    jpql += " o." + property + "=:" + property;
                }
                index++;
            }
        }

        jpql += " where ";
        if ((where != null) && (where.size() > 0)) {
            int first = 0;
            for (String property : where.keySet()) {
                if (first > 0) {
                    jpql += " and o." + property + "=:" + property;
                } else {
                    jpql += " o." + property + "=:" + property;
                }
                first++;
            }
        }
  
        Query query = entityManager.createQuery(jpql);
        if (params != null) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }
        if (where != null) {
            for (Map.Entry<String, Object> param : where.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }
        int ret = query.executeUpdate();
        return ret;
    }

    @Transactional
    public int updateByProperties(Map<String, Object> params, Map<String, Object> where) {
        int num = updateByPropertiesNoTrans(params, where);
        flush();
        return num;
    }



	public List<T> findByProperties(Map<String, Object> params) {
		String jpql = " select o from " + entityClass.getSimpleName()
				+ " o where 1=1 ";
		if (params != null) {
			for (String property : params.keySet()) {
				jpql += " and o." + property + "=:" + property;
			}
		}
		Query query = entityManager.createQuery(jpql, entityClass);
		if (params != null) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}

        List<T> result = query.getResultList();
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result;
        }
	}
	
	public List<T> findByTimeRange(Map<String, Object> params, String timeField, long startTime, long endTime) {
		String jpql = " select o from " + entityClass.getSimpleName() + " o where 1=1 ";
		if (params != null) {
			for (String property : params.keySet()) {
				jpql += " and o." + property + "=:" + property;
			}
		}
		jpql += " and o." + timeField + ">=" + startTime + " and o." + timeField + "<=" + endTime + " order by o." + timeField + " asc";
		
		Query query = entityManager.createQuery(jpql, entityClass);
		if (params != null) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}

        List<T> result = query.getResultList();
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result;
        }
	}

    public List<T> findByDateTimeRange(Map<String, Object> params, String timeField, Date startTime, Date endTime) {
        String jpql = " select o from " + entityClass.getSimpleName() + " o where 1=1 ";
        if (params != null) {
            for (String property : params.keySet()) {
                jpql += " and o." + property + "=:" + property;
            }
        }

        jpql += " and o." + timeField + ">='" + startTime.toString() + "' and o." + timeField + "<'" + endTime.toString() + "' order by o." + timeField + " asc";

        Query query = entityManager.createQuery(jpql, entityClass);
        if (params != null) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }

        List<T> result = query.getResultList();
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result;
        }
    }
	
	public List<T> findByTimeRangeBpsOrder(Map<String, Object> params, String timeField, long startTime, long endTime) {
		String jpql = " select o from " + entityClass.getSimpleName() + " o where 1=1 ";
		if (params != null) {
			for (String property : params.keySet()) {
				jpql += " and o." + property + "=:" + property;
			}
		}
		jpql += " and o." + timeField + ">=" + startTime + " and o." + timeField + "<=" + endTime + " order by o." + "bps_all" + " desc";
		
		Query query = entityManager.createQuery(jpql, entityClass);
		if (params != null) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}

        List<T> result = query.getResultList();
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result;
        }
	}

	public List<T> findByProperties(Map<String, Object> params, int start,
			int limit) {
		String jpql = " select o from " + entityClass.getSimpleName()
				+ " o where 1=1 ";
		if (params != null) {
			for (String property : params.keySet()) {
				jpql += " and o." + property + "=:" + property;
			}
		}
		Query query = entityManager.createQuery(jpql, entityClass);
		if (params != null) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	public List<T> findOrderedByProperties(Map<String, Object> params,
			Map<String, String> orders) {
		StringBuilder jpql = new StringBuilder(" select o from "
				+ entityClass.getSimpleName() + " o where 1=1 ");
		if (params != null) {
			for (String property : params.keySet()) {
				jpql.append(" and o." + property + "=:" + property);
			}
		}
		if (orders != null && orders.size() > 0) {
			jpql.append(" order by ");
			for (Map.Entry<String, String> entry : orders.entrySet()) {
				jpql.append("o." + entry.getKey() + " " + entry.getValue()
						+ ",");
			}
			jpql.deleteCharAt(jpql.length() - 1);
		}

		Query query = entityManager.createQuery(jpql.toString(), entityClass);
		if (params != null) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		return query.getResultList();
	}
	
	//根据关键字params 进行查找，并按照orders进行排序。params、orders可包含多个关键字
    public List<T> findOrderedByTimeListProperties(Map<String, Object> params,
                                           List<OrderParam> orders, String timeField, long startTime, long endTime ) {
        StringBuilder jpql = new StringBuilder(" select o from "
                + entityClass.getSimpleName() + " o where 1=1 ");
        if (params != null) {
            for (String property : params.keySet()) {
                jpql.append(" and o." + property + "=:" + property);
            }
        }
        
        jpql.append(" and o." + timeField + ">=" + startTime + " and o." + timeField + "<=" + endTime);
        
        if (orders != null && orders.size() > 0) {
            jpql.append(" order by ");
            for (OrderParam param : orders) {
                jpql.append("o." + param.getKey() + " " + param.getValue()
                        + ",");
            }
            jpql.deleteCharAt(jpql.length() - 1);
        }

        System.out.println(jpql);
        Query query = entityManager.createQuery(jpql.toString(), entityClass);
        if (params != null) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }
        return query.getResultList();
    }
	

    //根据关键字params 进行查找，并按照orders进行排序。params、orders可包含多个关键字
    public List<T> findOrderedByListProperties(Map<String, Object> params,
                                           List<OrderParam> orders) {
        StringBuilder jpql = new StringBuilder(" select o from "
                + entityClass.getSimpleName() + " o where 1=1 ");
        if (params != null) {
            for (String property : params.keySet()) {
                jpql.append(" and o." + property + "=:" + property);
            }
        }
        if (orders != null && orders.size() > 0) {
            jpql.append(" order by ");
            for (OrderParam param : orders) {
                jpql.append("o." + param.getKey() + " " + param.getValue()
                        + ",");
            }
            jpql.deleteCharAt(jpql.length() - 1);
        }

        System.out.println(jpql);
        Query query = entityManager.createQuery(jpql.toString(), entityClass);
        if (params != null) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }
        return query.getResultList();
    }

	public List<T> findOrderedByProperties(Map<String, Object> params,
			Map<String, String> orders, int start, int limit) {
		StringBuilder jpql = new StringBuilder(" select o from "
				+ entityClass.getSimpleName() + " o where 1=1 ");
		if (params != null) {
			for (String property : params.keySet()) {
				jpql.append(" and o." + property + "=:" + property);
			}
		}
		if (orders != null && orders.size() > 0) {
			jpql.append(" order by ");
			for (Map.Entry<String, String> entry : orders.entrySet()) {
				jpql.append("o." + entry.getKey() + " " + entry.getValue()
						+ ",");
			}
			jpql.deleteCharAt(jpql.length() - 1);
		}

		Query query = entityManager.createQuery(jpql.toString(), entityClass);
		if (params != null) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}




    public List<T> findOrderedByProperties(List<Map<String, Object>> paramsList, List<String> conditionList,
                                           Map<String, String> orders, int start, int limit) {
        StringBuilder jpql = new StringBuilder(" select o from "
                + entityClass.getSimpleName() + " o where 1=1 ");
        for (int i = 0; i < conditionList.size(); i++) {
            Map<String, Object> params = paramsList.get(i);
            for (String property : params.keySet()) {
                jpql.append(" and o." + property + conditionList.get(i) + ":" + property);
            }
        }

        if (orders != null && orders.size() > 0) {
            jpql.append(" order by ");
            for (Map.Entry<String, String> entry : orders.entrySet()) {
                jpql.append("o." + entry.getKey() + " " + entry.getValue()
                        + ",");
            }
            jpql.deleteCharAt(jpql.length() - 1);
        }

        Query query = entityManager.createQuery(jpql.toString(), entityClass);
        for (int i = 0; i < paramsList.size(); i++) {
            Map<String, Object> params = paramsList.get(i);
            for (Map.Entry<String, Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }

        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<T> findOrderedByProperties(List<Map<String, Object>> paramsList, List<String> conditionList,
                                           Map<String, String> orders) {
        StringBuilder jpql = new StringBuilder(" select o from "
                + entityClass.getSimpleName() + " o where 1=1 ");
        for (int i = 0; i < conditionList.size(); i++) {
            Map<String, Object> params = paramsList.get(i);
            for (String property : params.keySet()) {
                jpql.append(" and o." + property + conditionList.get(i) + ":" + property);
            }
        }

        if (orders != null && orders.size() > 0) {
            jpql.append(" order by ");
            for (Map.Entry<String, String> entry : orders.entrySet()) {
                jpql.append("o." + entry.getKey() + " " + entry.getValue()
                        + ",");
            }
            jpql.deleteCharAt(jpql.length() - 1);
        }

        Query query = entityManager.createQuery(jpql.toString(), entityClass);
        for (int i = 0; i < paramsList.size(); i++) {
            Map<String, Object> params = paramsList.get(i);
            for (Map.Entry<String, Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }
        return query.getResultList();
    }


    public List<T> findOrderedDescById(int start, int limit) {
        Map<String, String> orders = new HashMap<String, String>();
        orders.put("id", "desc");
        return findOrderedByProperties(null, orders, start, limit);
    }

    public List<T> findOrderedDescById(List<Map<String, Object>> paramsList, List<String> conditionList, int start, int limit) {
        Map<String, String> orders = new HashMap<String, String>();
        orders.put("id", "desc");
        return findOrderedByProperties(paramsList, conditionList, orders, start, limit);
    }

	public List<T> find(String jpql, Object... params) {
		Query query = entityManager.createQuery(jpql, entityClass);
		int i = 1;
		for (Object param : params) {
			query.setParameter(i, param);
			i++;
		}
		return query.getResultList();
	}
	
	public List<T> find(String jpql, List<Object> parmas){
		Query query = entityManager.createQuery(jpql, entityClass);
		int i = 1;
		for (Object param : parmas) {
			query.setParameter(i, param);
			i++;
		}
		return query.getResultList();
	}


	public List<T> findByRange(String jpql, int start, int limit, Object... params) {
		Query query = entityManager.createQuery(jpql, entityClass);
		int i = 1;
		for (Object param : params) {
			query.setParameter(i, param);
			i++;
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	public List<T> findAll() {
		return entityManager.createQuery(
				"select o from " + entityClass.getSimpleName() + " o")
				.getResultList();
	}

	public List<T> find(int start, int limit) {
		Query query = entityManager.createQuery("select o from "
				+ entityClass.getSimpleName() + " o");
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	public void flush() {
		entityManager.flush();
	}

	@Transactional
	public int deleteAll() {
		String jpql = " delete  from " + entityClass.getSimpleName();
		return entityManager.createQuery(jpql).executeUpdate();
	}

    public void deleteNoTrans(Serializable id) {
        T t = get(id);
        if (t == null) {
            return;
        }
        deleteNoTrans(t);
    }

	@Transactional
	public void delete(Serializable id) {
        T t = get(id);
        if (t == null) {
            return;
        }
        deleteNoTrans(t);
        entityManager.flush();
	}

    @Transactional
    public void delete(List<Integer> ids) {
        for (int id : ids) {
            deleteNoTrans(id);
        }

        entityManager.flush();
    }
	
	@Transactional
	public void deleteByProperties(Map<String, Object> params){
		String jpql = " delete from " + entityClass.getSimpleName()
				+ " o where 1=1 ";
		if (params != null) {
			for (String property : params.keySet()) {
				jpql += " and o." + property + "=:" + property;
			}
		} 
		//entityManager.getTransaction().begin();  
		Query query = entityManager.createQuery(jpql);
		if (params != null) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		query.executeUpdate();
		//entityManager.getTransaction().commit();
	}

    @Transactional
    public void deleteByProperties(List<Map<String, Object>> paramsList, List<String> conditionList) {
        String jpql = " delete from " + entityClass.getSimpleName()
                + " o where 1=1 ";
        for (int i = 0; i < conditionList.size(); i++) {
            Map<String, Object> params = paramsList.get(i);
            for (String property : params.keySet()) {
                jpql += " and o." + property + conditionList.get(i) + ":" + property;
            }
        }

        //entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(jpql);
        for (int i = 0; i < paramsList.size(); i++) {
            Map<String, Object> params = paramsList.get(i);
            for (Map.Entry<String, Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }

        query.executeUpdate();
        //entityManager.getTransaction().commit();
    }

	public Long countByProperties(Map<String, Object> params) {
		String jpql = " select count(o) from " + entityClass.getSimpleName()
				+ " o where 1=1 ";
		for (String property : params.keySet()) {
			jpql += " and o." + property + "=:" + property;
		}
		Query query = entityManager.createQuery(jpql, entityClass);
		for (Map.Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		return (Long) query.getSingleResult();
	}
	
	public Long countByCondition(List<Map<String, Object>> paramsList, List<String> conditionList){
		String jpql = " select count(o) from " + entityClass.getSimpleName()
				+ " o where 1=1 ";
		for (int i = 0; i < conditionList.size(); i++) {
			Map<String, Object> params = paramsList.get(i);
			for (String property : params.keySet()) {
				jpql += " and o." + property + conditionList.get(i) + ":" + property;
			}
		}
		Query query = entityManager.createQuery(jpql/*, entityClass*/);
		for (int i = 0; i < paramsList.size(); i++) {
			Map<String, Object> params = paramsList.get(i);
			for (Map.Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		return (Long) query.getSingleResult();
	}

	public Long countAll() {
		return (Long) entityManager.createQuery(
				"select count(o) from " + entityClass.getSimpleName() + " o")
				.getSingleResult();
	}

    //根据表的一个关键字求和
    public Long sumByProperty(String parm) {
        String jpql = " select sum(o." + parm +") from " + entityClass.getSimpleName()
                + " o where 1=1 ";

        Query query = entityManager.createQuery(jpql);

        return (Long) query.getSingleResult();
    }
}
