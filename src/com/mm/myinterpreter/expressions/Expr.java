package com.mm.myinterpreter.expressions;

import java.util.List;

import com.mm.myinterpreter.Token;
abstract class Expr {

    interface Visitor<T> {
    T visitBinaryExpr(Binary expr);
    T visitGroupingExpr(Grouping expr);
    T visitLiteralExpr(Literal expr);
    T visitUnaryExpr(Unary expr);
    }

    abstract <T> T accept(Visitor<T> visitor);

    static class Binary extends Expr {
    Binary(Expr left, Token operator, Expr right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
    }

    @Override
    <T> T accept(Visitor<T> visitor) {
    return visitor.visitBinaryExpr(this);
   }

    final Expr left;
    final Token operator;
    final Expr right;
    }
    static class Grouping extends Expr {
    Grouping(Expr expression) {
    this.expression = expression;
    }

    @Override
    <T> T accept(Visitor<T> visitor) {
    return visitor.visitGroupingExpr(this);
   }

    final Expr expression;
    }
    static class Literal extends Expr {
    Literal(Object value) {
    this.value = value;
    }

    @Override
    <T> T accept(Visitor<T> visitor) {
    return visitor.visitLiteralExpr(this);
   }

    final Object value;
    }
    static class Unary extends Expr {
    Unary(Token operator, Expr right) {
    this.operator = operator;
    this.right = right;
    }

    @Override
    <T> T accept(Visitor<T> visitor) {
    return visitor.visitUnaryExpr(this);
   }

    final Token operator;
    final Expr right;
    }
}
