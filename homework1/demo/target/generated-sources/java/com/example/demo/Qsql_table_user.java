package com.example.demo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * Qsql_table_user is a Querydsl query type for sql_table_user
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class Qsql_table_user extends EntityPathBase<sql_table_user> {

    private static final long serialVersionUID = -41119065L;

    public static final Qsql_table_user sql_table_user = new Qsql_table_user("sql_table_user");

    public final NumberPath<Integer> user_coin = createNumber("user_coin", Integer.class);

    public final StringPath user_id = createString("user_id");

    public final StringPath user_name = createString("user_name");

    public final StringPath user_type = createString("user_type");

    public Qsql_table_user(String variable) {
        super(sql_table_user.class, forVariable(variable));
    }

    public Qsql_table_user(Path<? extends sql_table_user> path) {
        super(path.getType(), path.getMetadata());
    }

    public Qsql_table_user(PathMetadata metadata) {
        super(sql_table_user.class, metadata);
    }

}

