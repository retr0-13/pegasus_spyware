package com.lenovo.performancecenter.framework;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;
/* loaded from: classes.dex */
public interface ICustomerWhiteListService extends IInterface {
    List<String> getCustomerWhiteList() throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements ICustomerWhiteListService {
        private static final String DESCRIPTOR = "com.lenovo.performancecenter.framework.ICustomerWhiteListService";
        static final int TRANSACTION_getCustomerWhiteList = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICustomerWhiteListService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ICustomerWhiteListService)) {
                return new Proxy(obj);
            }
            return (ICustomerWhiteListService) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TRANSACTION_getCustomerWhiteList /* 1 */:
                    data.enforceInterface(DESCRIPTOR);
                    List<String> _result = getCustomerWhiteList();
                    reply.writeNoException();
                    reply.writeStringList(_result);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements ICustomerWhiteListService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.lenovo.performancecenter.framework.ICustomerWhiteListService
            public List<String> getCustomerWhiteList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getCustomerWhiteList, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createStringArrayList();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
