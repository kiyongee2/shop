package com.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = 1074569221L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrders orders = new QOrders("orders");

    public final com.shop.config.QBaseEntity _super = new com.shop.config.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final ListPath<OrderItem, QOrderItem> orderItems = this.<OrderItem, QOrderItem>createList("orderItems", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final EnumPath<com.shop.constant.OrderStatus> orderStatus = createEnum("orderStatus", com.shop.constant.OrderStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QOrders(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QOrders(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrders(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QOrders(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

