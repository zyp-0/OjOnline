package org.example.utils;

import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.stmt.*;

public class ComplexityCalculator {
    public int evaluate(String path) {
        try {
            // Parse the Java file
            FileInputStream file = new FileInputStream(path);
            CompilationUnit cu = new JavaParser().parse(file).getResult().get();

            AtomicInteger totalComplexity = new AtomicInteger();

            // Calculate complexity for each method in the class
            cu.findAll(MethodDeclaration.class).forEach(method -> {
                int methodComplexity = calculateMethodComplexity(method);
                System.out.println(method.getNameAsString() + "函数圈复杂度：" + methodComplexity);
                totalComplexity.addAndGet(methodComplexity);
            });

            return totalComplexity.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static int calculateMethodComplexity(MethodDeclaration method) {
        AtomicInteger complexity = new AtomicInteger(1); // Start with 1 for the method itself

        method.findAll(IfStmt.class).forEach(ifStmt -> {
            complexity.addAndGet(ifStmt.getElseStmt().isPresent() ? 1 : 0); // Account for the 'if' statement itself
            if (ifStmt.getElseStmt().isPresent()) {
                complexity.addAndGet(ifStmt.getElseStmt().get().findAll(IfStmt.class).size()); // Add the number of 'else if' statements
            }
        });

        method.findAll(WhileStmt.class).forEach(whileStmt -> complexity.incrementAndGet());
        method.findAll(DoStmt.class).forEach(doStmt -> complexity.incrementAndGet());
        method.findAll(ForStmt.class).forEach(forStmt -> complexity.incrementAndGet());
        method.findAll(ConditionalExpr.class).forEach(conditionalExpr -> complexity.incrementAndGet());
        method.findAll(BinaryExpr.class).forEach(binaryExpr -> {
            if (binaryExpr.getOperator() == BinaryExpr.Operator.AND || binaryExpr.getOperator() == BinaryExpr.Operator.OR) {
                complexity.incrementAndGet();
            }
        });

        return complexity.get();
    }
}
