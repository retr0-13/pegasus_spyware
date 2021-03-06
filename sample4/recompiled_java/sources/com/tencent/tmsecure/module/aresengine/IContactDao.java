package com.tencent.tmsecure.module.aresengine;

import com.tencent.tmsecure.module.aresengine.ContactEntity;

public interface IContactDao<T extends ContactEntity> {
    public static final int CALL_FROM_CALLFILTER = 0;
    public static final int CALL_FROM_SMSFILTER = 1;

    boolean contains(String str, int i);
}
