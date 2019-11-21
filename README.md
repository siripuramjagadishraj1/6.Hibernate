# 6.Hibernate
Incomplete Stuff

	@AttributeOverrides
	@Embeddable
	


Tasks Completed:
----------------
	1. Collection Mapping [LIST, SET, MAP].. BAG..?
	2. Inheritance Mapping
		a. MAPPED_SUPER_CLASS
		b. SINGLE_TABLE
		c. JOINED
		d. TABLE_PER_CLASS
	3. Association Mapping
		a. One-to-One   (Unidirectional/ Bi-directional)
		b. One-to-Many  (Unidirectional/ Bi-directional)
		c. Many-to-Many (Unidirectional/ Bi-directional)
	4. Hibernate Query Languages:
		a. HQL          ss.createQuery
		b. SQL	         ss.createSQLQuery
		c. Criteria     ss.createCriteria
		d. NamedQuerries, Native, QBC, Polymorphic..??
		//Parameters: positional, named
		
		Pagination..??
		Optional..??
	
	5. Connection providers
		a. DriverManagerConnectionProvider[default]
		b. C3P0ConnectionProvider
		c. DataSourceConnectionProvider
		d. CustomConnectionProvider

	6. Hibernate Transactions
		a. JDBCTransactions
		b. JTATransactions
		c. CMTTransactions
		
		
		
		
		
	
	
For Depricated methods:
-----------------------
https://github.com/hibernate/hibernate-orm/wiki/Migration-Guides

Questions:
---------
	First level Cache questions:
	1. session.load() vs session.get() [former throws exception, later gives null if not found]
	2. session.save() vs session.persist()..??
	3. session.evict({}) vs session.clear() vs sessoin.contains({}): evict removes 1 object from cache, clear removes all objects from cache.
	
	Second level cache questions:
	1. Cahcing Strategies: READ, READ-WRITE, NON-Restricted READ-WRRITE, TRANSACTIONAL
	2. Options: diskStore,defaultCache, cacheName, cache Regions. 
	
	3. Generation types, AUTO, IDENTITY..etc
	4. LOADING: EAGER, 
	5. CASCADING:
	
Transactional Problems:
-----------------------
	1. PHANTOM READ
	2. REPEATABLE READ
	3. ??
	
Caching:
--------
	1. 1st Level Cache:
		a. Session Factory Cache
		b. Session Cache
		It's enabled by default and should not be disabled.
	2. 2nd Level Cache
		a. Query cache is 2nd level cache.
		b. can be process scope or clustered scope
		c. Disabled by default should eanble it

Providers:
---------
	1. HashtableCacheProvider
	2. EhCacheProvider(*)
	3. OSCacheProvider
	4. SwarmCacheProvider
	5. TreeCacheProvider(*)[JBoss Cache Provider]

States of Objects:
------------------
	1. Transient State
	2. Persistent State
	3. Detached State
	
Primary Keys:
-------------
	1. increment
	2. hilo
	3. Seqence (**) Oracle Sequence generator
	4. seqhilo - Oracle
	5. uuid
	6. guid
	7. identity
	8. native
	9. assigned
	10. select
	
	11. auto
	12. Identity
	13. sequence
	14. table