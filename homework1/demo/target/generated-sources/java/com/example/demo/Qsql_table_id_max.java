package com.example.demo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * Qsql_table_id_max is a Querydsl query type for sql_table_id_max
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class Qsql_table_id_max extends EntityPathBase<sql_table_id_max> {

    private static final long serialVersionUID = -1218298852L;

    public static final Qsql_table_id_max sql_table_id_max = new Qsql_table_id_max("sql_table_id_max");

    public final NumberPath<Long> id_max = createNumber("id_max", Long.class);

    public final StringPath type = createString("type");

    public Qsql_table_id_max(String variable) {
        super(sql_table_id_max.class, forVariable(variable));
    }

    public Qsql_table_id_max(Path<? extends sql_table_id_max> path) {
        super(path.getType(), path.getMetadata());
    }

    public Qsql_table_id_max(PathMetadata metadata) {
        super(sql_table_id_max.class, metadata);
    }

}

