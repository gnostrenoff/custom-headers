package io.moi;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class HeaderAgent {

    public static void premain(final String agentArgs, final Instrumentation inst) {
        new AgentBuilder.Default()
                .type(named("org.apache.catalina.core.StandardEngineValve"))
                .transform((builder, typeDescription, classLoader, module) -> builder
                        .method(named("invoke"))
                        .intercept(MethodDelegation.to(HeaderInterceptor.class)))
                .installOn(inst);
    }
}
