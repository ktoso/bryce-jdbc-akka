Using different journals in Akka Sharding / Persistence
======

```
DROP TABLE IF EXISTS ktoso.journal;  

CREATE TABLE IF NOT EXISTS ktoso.journal (
  persistence_id VARCHAR(255) NOT NULL,
  sequence_number BIGINT NOT NULL,
  created BIGINT NOT NULL,
  tags VARCHAR(255) DEFAULT NULL,
  message BYTEA NOT NULL,
  PRIMARY KEY(persistence_id, sequence_number)
);

DROP TABLE IF EXISTS ktoso.deleted_to;

CREATE TABLE IF NOT EXISTS ktoso.deleted_to (
  persistence_id VARCHAR(255) NOT NULL,
  deleted_to BIGINT NOT NULL
);

DROP TABLE IF EXISTS ktoso.snapshot;

CREATE TABLE IF NOT EXISTS ktoso.snapshot (
  persistence_id VARCHAR(255) NOT NULL,
  sequence_number BIGINT NOT NULL,
  created BIGINT NOT NULL,
  snapshot BYTEA NOT NULL,
  PRIMARY KEY(persistence_id, sequence_number)
);



DROP TABLE IF EXISTS ktoso.sharding_journal;  

CREATE TABLE IF NOT EXISTS ktoso.sharding_journal (
  persistence_id VARCHAR(255) NOT NULL,
  sequence_number BIGINT NOT NULL,
  created BIGINT NOT NULL,
  tags VARCHAR(255) DEFAULT NULL,
  message BYTEA NOT NULL,
  PRIMARY KEY(persistence_id, sequence_number)
);

DROP TABLE IF EXISTS ktoso.sharding_deleted_to;

CREATE TABLE IF NOT EXISTS ktoso.sharding_deleted_to (
  persistence_id VARCHAR(255) NOT NULL,
  deleted_to BIGINT NOT NULL
);

DROP TABLE IF EXISTS ktoso.sharding_snapshot;

CREATE TABLE IF NOT EXISTS ktoso.sharding_snapshot (
  persistence_id VARCHAR(255) NOT NULL,
  sequence_number BIGINT NOT NULL,
  created BIGINT NOT NULL,
  snapshot BYTEA NOT NULL,
  PRIMARY KEY(persistence_id, sequence_number)
);
```