package demo.profiler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Aspect
@Component
public class ServiceProfiler {
    static final Logger logger = LogManager.getLogger(ServiceProfiler.class);


    /**
     *
     * @param proceedingJoinPoint
     * @return Object
     * @throws Throwable
     */
   /* @Around("execution(* saedge.example.controller.*.*(..))")*/
    @Around("execution(* demo.controller.*.*(..))")
    public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
      
        
    	return startProfiling(proceedingJoinPoint , "");
    }

    /**
     *
     * @param proceedingJoinPoint
     * @return Object
     * @throws Throwable
     */
    @Around("execution(* demo.util.GenericUtil.*(..))")
    public Object profileCommonClient(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
       return startProfiling(proceedingJoinPoint , "Third Party Call");
    }

    /**
     *
     * @param proceedingJoinPoint
     * @param layer
     * @return Object
     * @throws Throwable
     */
     Object startProfiling(ProceedingJoinPoint proceedingJoinPoint, String layer) throws Throwable {
   
    	
    	
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(proceedingJoinPoint.toShortString());
        boolean isExceptionThrown = false;
        try {
            // execute the profiled method
            long start = System.currentTimeMillis();
            logger.info("Method  Execution at "+layer+" started");
            Object output= proceedingJoinPoint.proceed();
            logger.info("Method execution at "+layer+" completed.");
            long elapsedTime = System.currentTimeMillis() - start;
            logger.info("Total "+layer+" Method execution time: " + elapsedTime + " milliseconds.");
       
            return output;
        } catch (RuntimeException e) {
            isExceptionThrown = true;
            throw e;
        } finally {
        	
            stopWatch.stop();
            StopWatch.TaskInfo taskInfo = stopWatch.getLastTaskInfo();
            // Log the method's profiling result
            String profileMessage = taskInfo.getTaskName() + ": " + taskInfo.getTimeMillis() + " ms at "+layer+"" +
                    (isExceptionThrown ? " (thrown Exception)" : "");
            logger.info(profileMessage);
        }

    }

}
