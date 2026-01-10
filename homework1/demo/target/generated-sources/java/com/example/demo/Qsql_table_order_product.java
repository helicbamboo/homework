package com.example.demo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * Qsql_table_order_product is a Querydsl query type for sql_table_order_product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class Qsql_table_order_product extends EntityPathBase<sql_table_order_product> {

    private static final long serialVersionUID = -1550574174L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final Qsql_table_order_product sql_table_order_product = new Qsql_table_order_product("sql_table_order_product");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final Qsql_table_order_user order_id;

    public final StringPath product_id = createString("product_id");

    public final StringPath product_name = createString("product_name");

    public final NumberPath<Integer> product_price = createNumber("product_price", Integer.class);

    public final NumberPath<Integer> product_quantity = createNumber("product_quantity", Integer.class);

    public final Qsql_table_user product_seller_id;

    public final NumberPath<Integer> product_status = createNumber("product_status", Integer.class);

    public Qsql_table_order_product(String variable) {
        this(sql_table_order_product.class, forVariable(variable), INITS);
    }

    public Qsql_table_order_product(Path<? extends sql_table_order_product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public Qsql_table_order_product(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public Qsql_table_order_product(PathMetadata metadata, PathInits inits) {
        this(sql_table_order_product.class, metadata, inits);
    }

    public Qsql_table_order_product(Class<? extends sql_table_order_product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order_id = inits.isInitialized("order_id") ? new Qsql_table_order_user(forProperty("order_id"), inits.get("order_id")) : null;
        this.product_seller_id = inits.isInitialized("product_seller_id") ? new Qsql_table_user(forProperty("product_seller_id")) : null;
    }

}

