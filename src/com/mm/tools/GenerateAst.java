package com.mm.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException{
        if(args.length != 1)
        {
            System.out.println("generate_ast ");
            System.exit(64);
        }

        String outputDir = args[0];

        defineAst(outputDir, "Expr", Arrays.asList(
            "Binary   : Expr left, Token operator, Expr right",
            "Grouping : Expr expression",
            "Literal  : Object value",
            "Unary    : Token operator, Expr right"
        ));
    }

    private static void defineAst(String outputDir, String baseClassName, List<String> types) throws IOException
    {
        String path = outputDir + "/" + baseClassName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        
        writer.println("package com.mm.myinterpreter;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println("abstract class " + baseClassName + " {");

        defineVisitors(writer, baseClassName, types);

        for(String subType : types)
        {
            String[] splittedType = subType.split(":");
            String className = splittedType[0].trim();
            String fields = splittedType[1].trim();
            defineType(writer, baseClassName, className, fields);
        }

        //accept method visitor pattern
        writer.println("    abstract <T> T accept(Visitor<T> visitor);");

        writer.println("}");
        writer.close();
    }

    private static void defineType(PrintWriter writer, String baseClassName, String className, String fieldList)
    {
        //class name definition
        writer.println("    static class " + className + " extends " + baseClassName + " {");

        //constructor
        writer.println("    " + className + "(" + fieldList + ") {");

        String[] fields = fieldList.split(", ");
        for (String field : fields)
        {
            String name = field.split(" ")[1];
            writer.println("    this." + name + " = " + name + ";");
        }

        writer.println("    }");
        //visitor pattern 
        writer.println();
        writer.println("    @Override");
        writer.println("    <T> T accept(Visitor<T> visitor) {");
        writer.println("    return visitor.visit" + className + baseClassName + "(this);");
        
        writer.println("   }");
        //fields

        writer.println();
        for (String field : fields)
        {
            writer.println("    final " + field + ";");
        }

        writer.println("    }");   
    }

    private static void defineVisitors(PrintWriter writer, String baseAddress, List<String> types)
    {
        writer.println("    interface Visitor<T> {");

        for (String type : types)
        {
            String name = type.split(":")[0].trim();
            writer.println("    T visit" + name + baseAddress + "(" + name 
            + " " + baseAddress.toLowerCase() + ");" );
        }

        writer.println("    }");
    }
}
