package com.example.demo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * Qsql_table_topic is a Querydsl query type for sql_table_topic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class Qsql_table_topic extends EntityPathBase<sql_table_topic> {

    private static final long serialVersionUID = -1275723309L;

    public static final Qsql_table_topic sql_table_topic = new Qsql_table_topic("sql_table_topic");

    public final StringPath topic_group_id = createString("topic_group_id");

    public final StringPath topic_id = createString("topic_id");

    public final StringPath topic_name = createString("topic_name");

    public Qsql_table_topic(String variable) {
        super(sql_table_topic.class, forVariable(variable));
    }

    public Qsql_table_topic(Path<? extends sql_table_topic> path) {
        super(path.getType(), path.getMetadata());
    }

    public Qsql_table_topic(PathMetadata metadata) {
        super(sql_table_topic.class, metadata);
    }

}

