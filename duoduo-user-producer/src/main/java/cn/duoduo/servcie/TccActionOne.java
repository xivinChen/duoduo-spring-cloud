package cn.duoduo.servcie;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

public interface TccActionOne {

    @TwoPhaseBusinessAction(name = "tccActionOne",commitMethod = "commit",rollbackMethod = "cancel")
    boolean prepare(BusinessActionContext context,@BusinessActionContextParameter(paramName = "a")int a);
    boolean commit(BusinessActionContext context);
    boolean cancel(BusinessActionContext context);
}
