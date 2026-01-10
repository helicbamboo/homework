package com.example.demo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * Qsql_table_order_user is a Querydsl query type for sql_table_order_user
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class Qsql_table_order_user extends EntityPathBase<sql_table_order_user> {

    private static final long serialVersionUID = -688746504L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final Qsql_table_order_user sql_table_order_user = new Qsql_table_order_user("sql_table_order_user");

    public final StringPath order_id = createString("order_id");

    public final StringPath order_status = createString("order_status");

    public final DateTimePath<java.time.LocalDateTime> order_time = createDateTime("order_time", java.time.LocalDateTime.class);

    public final Qsql_table_user order_user_id;

    public Qsql_table_order_user(String variable) {
        this(sql_table_order_user.class, forVariable(variable), INITS);
    }

    public Qsql_table_order_user(Path<? extends sql_table_order_user> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public Qsql_table_order_user(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public Qsql_table_order_user(PathMetadata metadata, PathInits inits) {
        this(sql_table_order_user.class, metadata, inits);
    }

    public Qsql_table_order_user(Class<? extends sql_table_order_user> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order_user_id = inits.isInitialized("order_user_id") ? new Qsql_table_user(forProperty("order_user_id")) : null;
    }

}

