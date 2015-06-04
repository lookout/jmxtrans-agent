package org.jmxtrans.agent;

import javax.annotation.Nonnull;
import javax.management.ObjectName;
import java.util.concurrent.Callable;

/**
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
public interface ExpressionLanguageEngine {
    /**
     * Replace all the '#' based keywords (e.g. <code>#hostname#</code>) by their value.
     *
     * @param expression the expression to resolve (e.g. <code>"servers.#hostname#."</code>)
     * @return the resolved expression (e.g. <code>"servers.tomcat5"</code>)
     */
    @Nonnull
    String resolveExpression(@Nonnull String expression);

    /**
     * Replace all the '#' based keywords (e.g. <code>#hostname#</code>) by their value.
     * Replace all the '%' based keywords (e.g. <code>%type%</code>) by their keyProperty in ObjectName.
     *
     * @param expression the expression to resolve (e.g. <code>"servers.#hostname#.%name%"</code>)
     * @return the resolved expression (e.g. <code>"servers.tomcat5.PerFlow"</code>)
     */
    String resolveExpression(@Nonnull String expression, @Nonnull ObjectName exactObjectName);

    /**
     * Registers an expression evaluator with a static value.
     *
     * @param expression the expression key to lookup (e.g. <code>"attribute"</code>)
     * @param evaluator a Callable evaluator that returns String
     */
    void registerExpressionEvaluator(@Nonnull String expression, Callable<String> evaluator);

    /**
     * Registers an expression evaluator with a static value.
     *
     * @param expression the expression key to lookup (e.g. <code>"attribute"</code>)
     * @param value that replaces the expression (e.g. <code>"PerFlow"</code>)
     */
    void registerExpressionEvaluator(@Nonnull String expression, String value);
}
