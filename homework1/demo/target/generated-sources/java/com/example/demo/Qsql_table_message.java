package com.example.demo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * Qsql_table_message is a Querydsl query type for sql_table_message
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class Qsql_table_message extends EntityPathBase<sql_table_message> {

    private static final long serialVersionUID = 189766571L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final Qsql_table_message sql_table_message = new Qsql_table_message("sql_table_message");

    public final StringPath message_content = createString("message_content");

    public final StringPath message_id = createString("message_id");

    public final DateTimePath<java.time.LocalDateTime> message_time = createDateTime("message_time", java.time.LocalDateTime.class);

    public final Qsql_table_topic topic_id;

    public final Qsql_table_user user_id;

    public Qsql_table_message(String variable) {
        this(sql_table_message.class, forVariable(variable), INITS);
    }

    public Qsql_table_message(Path<? extends sql_table_message> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public Qsql_table_message(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public Qsql_table_message(PathMetadata metadata, PathInits inits) {
        this(sql_table_message.class, metadata, inits);
    }

    public Qsql_table_message(Class<? extends sql_table_message> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.topic_id = inits.isInitialized("topic_id") ? new Qsql_table_topic(forProperty("topic_id")) : null;
        this.user_id = inits.isInitialized("user_id") ? new Qsql_table_user(forProperty("user_id")) : null;
    }

}

