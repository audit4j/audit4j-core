package org.audit4j.core.Int;


public abstract class TimelyIntTest extends IntTestBase {
    
    /**
     * Before.
     */
    protected void before(Class<?> clazz){
        super.watchStart(clazz.getName());
    }
    
    /**
     * After.
     */
    @Override
    protected void after(){
        super.watchStop();
        watch.reset();
        watch = null;
    }
}
