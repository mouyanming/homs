--認証
create table if not exists users (
  user_id varchar(256),
  password varchar(256),
  display_name varchar(255),
  enabled boolean,
  usr_nm varchar(30),
  usr_bth date,
  usr_sex smallint(1),
  usr_mb varchar(15),
  usr_ml varchar(30),
  usr_ttl varchar(20),
  sp_usr_id varchar(20),
  jsg_kb varchar(1),
  ep_dt date,
  lf_dt date,
  ac_sts int(1) DEFAULT 0,
  pwd_err_cnt int(1) DEFAULT 0,
  upd_tm timestamp,
  crt_tm timestamp,
  primary key(user_id)
);

create table if not exists authorities (
  user_id varchar(256),
  authority varchar(256),
  primary key(user_id, authority),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);


-- remember me 認証
create table if not exists persistent_logins(
  username varchar(64) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
);