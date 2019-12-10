package cn.duoduo.servcie.impl;

import cn.duoduo.servcie.TccActionOne;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.stereotype.Service;

@Service
public class TccActionOneImpl implements TccActionOne {

    @Override
    public boolean prepare(BusinessActionContext context, int a) {
        String xid = context.getXid();
        System.out.println("prepare _____xid = " + xid+",a = "+a);
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext context) {
        String xid = context.getXid();
        Object a = context.getActionContext("a");
        System.out.println("commit ___xid="+xid+"a = " + a);
        return true;
    }

    @Override
    public boolean cancel(BusinessActionContext context) {
        String xid = context.getXid();
        Object a = context.getActionContext("a");
        System.out.println("cancel ___xid="+xid+"a = " + a);
        return false;
    }
}
