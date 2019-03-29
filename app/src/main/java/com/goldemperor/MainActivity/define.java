package com.goldemperor.MainActivity;

public class define {


//    public static final String endpoint = "http://jindi.zealji.com/";
    public static final String endpoint = "http://oss-cn-beijing.aliyuncs.com/";
    public static final String endpoints = "https://glodemperor.oss-cn-beijing.aliyuncs.com/";


    public static final String bucket = "glodemperor";


    public static String OSS_KEY = "LTAILVsmSDSxWo7D";
    public static String OSS_SECRET = "iWSOmHPErPqWRuC7vNrq5b7qS4hSDn";


    public static String suitID = "1";


    public static String IP1718881 = "http://geapp.goldemperor.com:8881/";

    public static String IP328030 = "http://59.56.103.32:8030/";

    public static String SERVER = "http://geapp.goldemperor.com";
    public static String PORT_8020 = ":8020/";//文件下载端口
    public static String PORT_8083 = ":8083/";//图库端口
    public static String PORT_8881 = ":8881/";//来料稽查

    public static String PORT_8012 = ":8012/";//正式库
    public static String PORT_8056 = ":8056/";//测试库
    public static String SERVER_XL = "http://192.168.101.233";//信利电脑测试库
    public static String PORT_8078 = ":8078/";//信利电脑测试库端口


    public static final String SubmitRequest = IP1718881 + "SubmitRequest";

    public static final String GetData = IP1718881 + "GetData";

    public static final String GetDataBydate = IP1718881 + "GetDataBydate";

    public static final String SubmitCheck = IP1718881 + "SubmitCheck";

    public static final String GetDataById = IP1718881 + "GetDataById";

    public static final String Done = IP1718881 + "Done";

    public static final String Exceptional = IP1718881 + "Exceptional";

    public static final String SubmitResult = IP1718881 + "SubmitResult";

    public static final String SubmitRefuse = IP1718881 + "SubmitRefuse";

    public static final String GetDataBySupplier = IP1718881 + "GetDataBySupplier";

    public static final String UpdataImage = IP1718881 + "UpdataImage";

    public static final String GetImage = IP1718881 + "GetImage";

    public static final String GetXJD = "getXJD";

    public static final String GetXJDPriceEntry = "getXJDPriceEntry";

    public static final String GetXJDReadyCheck = "getXJDReadyCheck";

    public static final String GetFACardByEmpNumber = "getFACardByEmpNumber";

    public static final String GetFempByFnumber = "getFempByFnumber";

    public static final String GetCompany = "getCompany";

    public static final String GetDepartmentOneByFjspaygroupingnumber = "getDepartmentOneByFjspaygroupingnumber";

    public static final String GetDepartmentTwoByFnumber = "getDepartmentTwoByFnumber";


    public static final String SetCheckerId = "setCheckerId";


    public static final String Login = IP328030 + "api/get/login";

    public static final String GetCode = IP328030 + "api/MessagePost/GetCode";

    public static final String WechatPostByFEmpID = IP328030 + "api/MessagePost/WechatPostByFEmpID";


    public static final String GetWorkCardInfoNew = "ErpForAndroidStockServer.asmx/GetWorkCardInfoNew";

    public static final String GetProcessWorkCardInfo = "ErpForAndroidStockServer.asmx/GetProcessWorkCardInfo";

    //    public static final String GetWorkCardInfoByMoNo= "ErpForAndroidStockServer.asmx/GetWorkCardInfoByMoNo";
    public static final String GetWorkCardInfoByMoNo = "ErpForAndroidStockServer.asmx/GetWorkCardInfoByMoNoNew";
//    public static final String SaveProcessWorkCardEntry = "ErpForAndroidStockServer.asmx/SaveProcessWorkCardEntry";
    public static final String APPSaveProcessWorkCard = "APPSaveProcessWorkCard";

    public static final String SetWorkCardCloseStatus = "ErpForAndroidStockServer.asmx/SetWorkCardCloseStatus";

    public static final String GetPrdRouteInfo = "ErpForMesServer.asmx/GetPrdRouteInfo";
    public static final String GetPrdRouteInfo2 = "GetPrdRouteInfo";

    public static final String SavePrdRouteEntry = "ErpForMesServer.asmx/SavePrdRouteEntry";

    public static final String DeletePrdRouteEntryByFID = "ErpForMesServer.asmx/DeletePrdRouteEntryByFID";

    public static final String GetManufactureInstructions = "ErpForMesServer.asmx/GetManufactureInstructions";

    //    public static final String GetEmpByDeptID = "wechatMvn/mes/GetEmpByDeptID.do";
//
//    public static final String GetEmpByFNumber = "wechatMvn/mes/GetEmpByFNumber.do";
    public static final String GetEmpByFnumber = "GetEmpByFNumber";



    //    public static final String GetRouteEntryBody = "wechatMvn/android/getRouteEntryBody.do";
    public static final String GetRouteEntryBody = "GetRouteEntryBody";


    //    public static final String GetWorkPlanMaterial = "wechatMvn/android/getWorkPlanMaterial.do";
    public static final String GetWorkPlanMaterial = "GetWorkPlanMaterial";


//    public static final String GetRouteEntryPic = "wechatMvn/android/getRouteEntryPic.do";

//    public static final String UpdateXML = "AndroidUpdate/GoldEmperor/update.xml";
    public static final String UpdateXML = "AndroidUpdate/GoldEmperor/GoldEmperorUpData.xml";

    public static final String GetScProcessWorkCardInfoBysuitID = "GetScProcessWorkCardInfoBysuitID";




    public static final String IsHaveControl = "ERPPublicServer.asmx/IsHaveControl";
    public static final String IsHaveControl2 = "IsHaveControl";
    public static final String IsHaveUserControl = "ERPPublicServer.asmx/IsHaveUserControl";
    public static final String IsHaveUserControl2 = "IsHaveUserControl";

    public static final String DeleteScProcessWorkCard = "ErpForAndroidStockServer.asmx/DeleteScProcessWorkCard";


    public static final String DeleteProcessWorkCardEntryByFID = "DeleteProcessWorkCardEntryByFID";

    public static final String SCWorkCard2SCProcessWorkCardBysuitID = "ErpForAndroidStockServer.asmx/SCWorkCard2SCProcessWorkCardBysuitID";
    public static final String SCWorkCard2SCProcessWorkCardNoInsert = "SCWorkCard2SCProcessWorkCardNoInsert";


    public static final String GetUserID = "ERPForWeixinServer.asmx/GetUserID";
    public static final String GetUserID2 = "GetUserID";
    public static final String ERPForWeixinServer = "ERPForWeixinServer.asmx";

    public static final String RecordProductionCount = "RecordProductionCount";

    public static final String GetWorkCardQtyPassInfo = "ErpForAndroidStockServer.asmx/GetWorkCardQtyPassInfo";


    public static final String GetProcessWorkCardInfoByWorkCardID = "ErpForAndroidStockServer.asmx/GetProcessWorkCardInfoByWorkCardID";


    public static final String GetDayWorkCardPlanQtyBysuitID = "ErpForAndroidStockServer.asmx/GetDayWorkCardPlanQtyBysuitID";


    public static final String WorkCardAbnormityBysuitID = "ErpForAndroidStockServer.asmx/WorkCardAbnormityBysuitID";

    //    public static final String GetWorkCardAbnormity = "wechatMvn/android/getWorkCardAbnormity.do";
    public static final String GetWorkCardAbnormity = "GetWorkCardAbnormity";

    //    public static final String GetAbnormityByName = "wechatMvn/android/getAbnormityByName.do";
    public static final String GetAbnormityByName = "GetAbnormityByName";

    //    public static final String GetAbnormityByID = "wechatMvn/android/getAbnormityByID.do";
    public static final String GetAbnormityByID = "GetAbnormityByID";

    //    public static final String GetDeptAll = "wechatMvn/mes/GetDeptAll.do";
    public static final String GetDeptAll = "GetDeptAll";

    //    public static final String DeleteByFInterID = "wechatMvn/android/deleteByFInterID.do";
    public static final String DeleteByFInterID = "DeleteByFInterID";

    //    public static final String ReCheckAbnormity = "wechatMvn/android/ReCheckAbnormity.do";
    public static final String ReCheckAbnormity = "ReCheckAbnormity";

    public static final String GetSfcOperPlanningInfo = "ErpForMesServer.asmx/GetSfcOperPlanningInfo";
    public static final String ResetWorkCardPushData = "ErpForAndroidStockServer.asmx/ResetWorkCardPushData";
    public static final String GetBarCodeStatus = "ErpForAndroidStockServer.asmx/GetBarCodeStatus";
    //    public static final String GetBarCodeStatusBysuitID = "ErpForAndroidStockServer.asmx/GetBarCodeStatusBysuitID";
    public static final String GetBarCodeStatusBysuitID2 = "GetBarCodeStatusBysuitID";
    public static final String GetHelpInfoList = "GetHelpInfoList";

    public static final String GetPrdShopDayReport = "GetPrdShopDayReport";
    public static final String GetDayOutPutLevelOneReport = "GetDayOutPutLevelOneReport";
    public static final String GetDayOutPutLevelSizeReport = "GetDayOutPutLevelSizeReport";
    public static final String GetDayWorkCardReport = "GetDayWorkCardReport";
    public static final String GetDayWorkCardReport2 = "GetDayWorkCardReport2";
    public static final String GetStockList = "GetStockList";
    public static final String GetDefaultStockIDForDB = "GetDefaultStockIDForDB";
    public static final String GetScMoNoPreviewFile = "GetScMoNoPreviewFile";
    public static final String GetPreviewFileByMoNo = "GetPreviewFileByMoNo";

    public static final String SharedName = "data";
    public static final String SharedDPI = "DPI";

    public static final String SharedPhone = "phone";

    public static final String SharedUser = "name";


    public static final String RememberPassword = "RememberPassword";
    public static final String Login_UserName = "Login_UserName";
    public static final String Login_UserPassword = "Login_UserPassword";

    public static final String BarCode_FPA = "BarCode_FPA";
    public static final String BarCode_PRIA = "BarCode_PRIA";
    public static final String BarCode_PRA = "BarCode_PRA";
    public static final String BarCode_PWA = "BarCode_PWA";
    public static final String BarCode_SA = "BarCode_SA";
    public static final String BarCode_WAA = "BarCode_WAA";

    public static final String SharedSuitID = "SharedSuitID";
    public static final String SharedOrganizeID = "SharedOrganizeID";

    public static final String SharedJobNumber = "JobNumber";

    public static final String SharedPassword = "password";

    public static final String SharedCheckNumber = "CheckNumber";

    public static final String SharedEmpId = "EmpId";

    public static final String SharedUserId = "UserId";
    public static final String SharedDefaultStockID = "DefaultStockID";
    public static final String SharedDiningRoomID = "SharedDiningRoomID";

    public static final String SharedSex = "sex";

    public static final String SharedPosition = "position";


    public static final String SharedProposer = "Proposer";

    public static final String SharedInfo = "Info";

    public static final String SharedAuditor = "Auditor";

    public static final String SharedPatternLock = "PatternLock";

    public static final String SharedCheckMessage = "CheckMessage";

    public static final String SharedWXPic = "WXPic";

    public static final String SharedFDeptmentid = "FDeptmentid";

    public static final String SharedFOrganizeid = "FOrganizeid";

    public static final String SharedSupplier = "SupplierActivity";

    public static final String SharedDeptmentName = "DeptmentName";
    public static final String LoginPhone = "LoginPhone";
    public static final String LoginType = "LoginType";

    public static final String NONE = "-1";

    public static final String WAIT = "1";
    public static final String READY = "2";
    public static final String DONE = "3";
    public static final String EXCEPTIONAL = "4";
    public static final String EXCEPTIONAL_RESULT = "5";
    public static final String CASECLOSE = "6";

    public static int UPDATA = 1;
    public static String CREATE_CODE_SUCCESS = "0";

    public static int SELECT_PHOTO = 1;
    public static int TAKE_PHOTO = 2;
//
//    public static boolean isWaiNet = false;
//
//    public static boolean isCeNet = false;

    public static String appKey = "afeeb3ab6b0090293a70a5ba1d26a478";
    public static String appSecret = "e3c0d24ddd06";
    public static String nonce = "98269826";
    public static String template_id = "T2gJu-16hG6W5BWZIYzjLVozzwOylpBeGPUXi2_zmOk";

    public static String QUALITY_FACTORYNAME = "Quality_FactoryName";
    public static String QUALITY_WORKINGPROCEDURE = "Quality_WorkingProcedure";
    public static String QUALITY_COURSE = "Quality_Course";
    public static String QUALITY_GROUP = "Quality_Group";

    public static String GetScMoSizeBarCodeByMONo = "GetScMoSizeBarCodeByMONo";


    public static String tempuri = "http://tempuri.org/";
    public static String jindishoes = "http://www.jindishoes.com/";
    public static String ErpForAndroidStockServer = "ErpForAndroidStockServer.asmx";
    public static String ErpForAndroidSever = "ErpForAndroidSever.asmx";
    public static String ErpForAppServer = "ErpForAppServer.asmx";
    public static String ErpPublicServer = "ErpPublicServer.asmx";
    public static String ErpForMesServer = "ErpForMesServer.asmx";
    public static String ERPForSupplierServer = "ERPForSupplierServer.asmx";
    public static String WEB_NEWGETSUBMITBARCODEREPORT = "NewGetSubmitBarCodeReport";
    public static String WEB_GETPROCESSOUTPUTREPORT = "GetProcessOutputReport";
    public static String WEB_GETPROCESSOUTPUTREPORTDETAIL = "GetProcessOutputReportDetail";
    public static String GetProcessFlowInfoByBarCode = "GetProcessFlowInfoByBarCode";
    public static String JudgeEmpNumber = "JudgeEmpNumber";
    public static String GetProcessFlowInfo = "GetProcessFlowInfo";
    public static String GetDeptment = "GetDeptment";
    public static String GetDataTableAccountSuit = "GetDataTableAccountSuit";
    public static String GetOrganization = "GetOrganization";
    public static String NewPhoneLogin = "NewPhoneLogin";
    public static String GetAbnormityDetail = "GetAbnormityDetail";
    public static String GetHourAllInfo = "GetHourAllInfo";
    public static String GetProDayLeaveFourSizeReportSource = "GetProDayLeaveFourSizeReportSource";
    public static String GetProOrderOfCastReport = "GetProOrderOfCastReport";
    public static String GetPictureList = "GetPictureList";


    public static String HTTP_GETCODE = IP328030 + "api/MessagePost/GetCode";


    public static final String Submit2PrdDayReportNote = "Submit2PrdDayReportNote";//日未完成达标提交
    public static final String SubmitBarCode2CkRequisitionSlipCollectBill = "SubmitBarCode2CkRequisitionSlipCollectBill";//调拨单
    public static final String SubmitBarCode2CollectBill = "SubmitBarCode2CollectBill";//供应商扫码入库
    public static final String SubmitWorkCardBarCode2CollectBill = "SubmitWorkCardBarCode2CollectBill";//生产汇报
    public static final String SubmitBarCode2PrdInStockCollectBill = "SubmitBarCode2PrdInStockCollectBill";//生产扫码入库
    public static final String SubmitCxBarCode2CollectBill = "SubmitCxBarCode2CollectBill";//成型后段扫码入库
    public static final String SubmitScWorkCardBarCode2ProcessOutput = "SubmitScWorkCardBarCode2ProcessOutput";//工序汇报入库
    public static final String SubmitProcessBarCode2CollectBill = "SubmitProcessBarCode2CollectBill";//工序汇报

    public static final String GetEarlyWarningInfoReportOne = "GetEarlyWarningInfoReportOne";//金帝集团车间生产日报表
    public static final String GetEarlyWarningInfoReportTwo = "GetEarlyWarningInfoReportTwo";//金帝集团达成异常预警报表
    public static final String GetUnnumberedProperty = "GetUnnumberedProperty";//获取所有未编号资产信息
    public static final String GetAssestByFInterID = "GetAssestByFInterID";//获取资产详细信息
    public static final String UpdateAssest = "UpdateAssest";//固定资产登记
    public static final String PrintAssestLabel = "PrintAssestLabel";//打印贴标
    public static final String AssestRegistrationClose = "AssestRegistrationClose";//入库关闭工单
    public static final String GetEmpAndLiableByEmpCode = "GetEmpAndLiableByEmpCode";
    public static final String SearchUnnumberedProperty = "SearchUnnumberedProperty";
    public static final String GetUnnumberedPropertyByEmpID = "GetUnnumberedPropertyByEmpID";
    public static final String OpenFixedAssets = "OpenFixedAssets";


}
