
var AM_ESA = {
    initCertList : function(target, opts) {
        try {
            var certs = CertStore.listAllCerts();
            if(certs == null || certs.size() <= 0){
                EasyUI.initCombobox(target, $.extend({data: [{text:'没有检测到证书', value:''}] }, opts));
                return;
            }else{
                var data = [], firstValue = '';
                for(var i = 0; i < certs.size(); i++){
                    var cert = certs.get(i);
                    var sn = cert.serialNumber();
                    var cn = AM_ESA.getCNFromSubject(cert);
                    data.push({text: cn, value: sn, subject: cert.subject() });
                    if(i == 0){
                        firstValue = sn;
                    }
                    $('#htm').html( $('#htm').html()+ "," + cn + "====" + sn);
                }
                EasyUI.initCombobox(target, $.extend({data: data, value: firstValue }, (opts || {})));
            }
        } catch (e) {
            if (e instanceof TCACErr) {
                alert(e.toStr());
            } else {
                alert("Here is Error !!!");
            }
        }
    },

    getCert: function(sn){
        var certs = CertStore.listAllCerts();
        if(!sn){
            sn = certs.get(0).serialNumber();
        }
        var r = certs.bySerialnumber(sn);
        return r.get(0);
    },

    getCNFromSubject : function (cert) {
        try {
            var t = cert.subject().match(
                /(S(?!N)|L|O(?!U)|OU|SN|CN|E)=([^=]+)(?=, |$)/g);
            for (var i = 0; i < t.length; i++) {
                if (t[i].indexOf("CN=") === 0)
                    return t[i].substr(3, t[i].length);
            }
            return null;
        } catch (e) {
            if (e instanceof TCACErr) {
                alert(e.toStr());
            } else {
                alert("Here is Error !!!");
            }
        }
    }
}