package com.ctrip.zeus.restful.resource;

import com.ctrip.zeus.lock.LockService;
import com.ctrip.zeus.lock.entity.LockList;
import com.ctrip.zeus.lock.entity.LockStatus;
import com.ctrip.zeus.restful.message.ResponseHandler;
import org.springframework.stereotype.Component;
import org.unidal.dal.jdbc.DalException;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by zhoumy on 2015/4/16.
 */
@Component
@Path("/lock")
public class LockResource {
    @Resource
    private LockService lockService;
    @Resource
    private ResponseHandler responseHandler;

    @GET
    @Path("/status")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStatus(@Context HttpHeaders hh) throws Exception {
        LockList ll = new LockList();
        for (LockStatus ls : lockService.getLockStatus()) {
            ll.addLockStatus(ls);
        }
        return responseHandler.handle(ll, hh.getMediaType());
    }

    @GET
    @Path("/unlock/{key}")
    public Response forceUnlock(@Context HttpHeaders hh, @PathParam("key") String key) throws DalException {
        lockService.forceUnlock(key);
        return Response.status(200).build();
    }
}