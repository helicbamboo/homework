package com.example.demo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * Qsql_table_group is a Querydsl query type for sql_table_group
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class Qsql_table_group extends EntityPathBase<sql_table_group> {

    private static final long serialVersionUID = -1287640285L;

    public static final Qsql_table_group sql_table_group = new Qsql_table_group("sql_table_group");

    public final StringPath group_id = createString("group_id");

    public final StringPath group_name = createString("group_name");

    public Qsql_table_group(String variable) {
        super(sql_table_group.class, forVariable(variable));
    }

    public Qsql_table_group(Path<? extends sql_table_group> path) {
        super(path.getType(), path.getMetadata());
    }

    public Qsql_table_group(PathMetadata metadata) {
        super(sql_table_group.class, metadata);
    }

}

