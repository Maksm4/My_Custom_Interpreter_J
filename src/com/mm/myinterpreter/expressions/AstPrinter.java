package com.mm.myinterpreter.expressions;

import com.mm.myinterpreter.expressions.Expr.*;

public class AstPrinter implements Expr.Visitor<String>{

    String print(Expr expr)
    {   
        return expr.accept(this);
    } 

    @Override
    public String visitBinaryExpr(Binary expr) 
    {
        return parenthesize(expr.operator.getLexeme(), expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Grouping expr) 
    {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Literal expr) 
    {
        if(expr.value == null) return null;
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Unary expr) 
    {
        return parenthesize(expr.operator.getLexeme(), expr.right);
    }

    private String parenthesize(String name, Expr... exprs)
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("(").append(name);

        for (Expr expr : exprs)
        {
            stringBuilder.append(" ");
            stringBuilder.append(expr.accept(this));
        }

        stringBuilder.append(")");
        return stringBuilder.toString();
    }    
}
