// ignore_for_file: avoid_print

class UserPermissions {
  final String? locaCode;
  final String? userCode;
  final String? cName;
  final String? userName;
  final String? userType;
  final String? pinnumber;
  final String? loginLocaCode;

  final bool chkAddItem;
  final bool chkEditItem;
  final bool chkDelItem;
  final bool chkAddCat;
  final bool chkEditCat;
  final bool chkDelCat;
  final bool chkAddSup;
  final bool chkEditSup;
  final bool chkDelSup;
  final bool chkAddCus;
  final bool chkEditCus;
  final bool chkDelCus;
  final bool chkAddEmp;
  final bool chkEditEmp;
  final bool chkDelEmp;
  final bool chkAddUser;
  final bool chkEditUser;
  final bool chkDelUser;
  final bool chkUserControl;
  final bool chkUserControlOption;
  final bool chkPurchase;
  final bool chkPurchaseR;
  final bool chkClearPurchase;
  final bool chkCancelPurchase;
  final bool chkEditPurchase;
  final bool chkHoldPurchase;
  final bool chkProceedGRN;
  final bool chkInvoice;
  final bool chkInvoiceR;
  final bool chkPrintInvoice;
  final bool chkClearInvoice;
  final bool chkCancelInvoice;
  final bool chkEditInvoice;
  final bool chkHoldInvoice;
  final bool chkMakeQuotation;
  final bool chkMakeDNote;
  final bool chkMakeReceipt;
  final bool chkDeleteHoldInv;
  final bool chkCreditSales;
  final bool chkPrintDaySummery;
  final bool chkPayDue;
  final bool chkQtyAdjust;
  final bool chkCashDenomination;
  final bool chkCashDiscount;
  final bool chkEmpDiscount;
  final bool chkUpdateCashDenomination;
  final bool chkViewCashDenominationRpt;
  final bool chkPaidOut;
  final bool chkSalaryPayment;
  final bool chkAddIncome;
  final bool chkAddExpenses;
  final bool chkProceedIncome;
  final bool chkProceedExpenses;
  final bool chkAccountDet;
  final bool chkEmpRpt;
  final bool chkCusRpt;
  final bool chkCatRpt;
  final bool chkSupRpt;
  final bool chkItemRpt;
  final bool chkStockRpt;
  final bool chkViewHome;
  final bool chkPriceChange;
  final bool chkChangeDate;
  final bool chkAddDCat;
  final bool chkEditDCat;
  final bool chkVenUpdate;
  final bool chkDescUpdate;
  final bool chkAddVatDet;
  final bool chkShowUPrice;
  final bool chkShowCost;
  final bool chkAddJBN;
  final bool chkUpdateJBN;
  final bool chkDeleteJBN;
  final bool chkEditChqDetails;
  final bool chkStockReplace;
  final bool chkStockeTransfer;
  final bool chkAddBank;
  final bool chkEditBank;
  final bool chkDelBank;
  final bool chkAddDeposit;
  final bool chkAddWithdraw;
  final bool chkWebAccess;
  final bool chkUpdateItemPrice;
  final bool chkDeleteItemPrice;
  final bool chkDataSync;
  final bool chkTOGReceived;

  UserPermissions({
    this.locaCode,
    this.userCode,
    this.cName,
    this.userName,
    this.userType,
    this.pinnumber,
    this.loginLocaCode,
    this.chkAddItem = false,
    this.chkEditItem = false,
    this.chkDelItem = false,
    this.chkAddCat = false,
    this.chkEditCat = false,
    this.chkDelCat = false,
    this.chkAddSup = false,
    this.chkEditSup = false,
    this.chkDelSup = false,
    this.chkAddCus = false,
    this.chkEditCus = false,
    this.chkDelCus = false,
    this.chkAddEmp = false,
    this.chkEditEmp = false,
    this.chkDelEmp = false,
    this.chkAddUser = false,
    this.chkEditUser = false,
    this.chkDelUser = false,
    this.chkUserControl = false,
    this.chkUserControlOption = false,
    this.chkPurchase = false,
    this.chkPurchaseR = false,
    this.chkClearPurchase = false,
    this.chkCancelPurchase = false,
    this.chkEditPurchase = false,
    this.chkHoldPurchase = false,
    this.chkProceedGRN = false,
    this.chkInvoice = false,
    this.chkInvoiceR = false,
    this.chkPrintInvoice = false,
    this.chkClearInvoice = false,
    this.chkCancelInvoice = false,
    this.chkEditInvoice = false,
    this.chkHoldInvoice = false,
    this.chkMakeQuotation = false,
    this.chkMakeDNote = false,
    this.chkMakeReceipt = false,
    this.chkDeleteHoldInv = false,
    this.chkCreditSales = false,
    this.chkPrintDaySummery = false,
    this.chkPayDue = false,
    this.chkQtyAdjust = false,
    this.chkCashDenomination = false,
    this.chkCashDiscount = false,
    this.chkEmpDiscount = false,
    this.chkUpdateCashDenomination = false,
    this.chkViewCashDenominationRpt = false,
    this.chkPaidOut = false,
    this.chkSalaryPayment = false,
    this.chkAddIncome = false,
    this.chkAddExpenses = false,
    this.chkProceedIncome = false,
    this.chkProceedExpenses = false,
    this.chkAccountDet = false,
    this.chkEmpRpt = false,
    this.chkCusRpt = false,
    this.chkCatRpt = false,
    this.chkSupRpt = false,
    this.chkItemRpt = false,
    this.chkStockRpt = false,
    this.chkViewHome = false,
    this.chkPriceChange = false,
    this.chkChangeDate = false,
    this.chkAddDCat = false,
    this.chkEditDCat = false,
    this.chkVenUpdate = false,
    this.chkDescUpdate = false,
    this.chkAddVatDet = false,
    this.chkShowUPrice = false,
    this.chkShowCost = false,
    this.chkAddJBN = false,
    this.chkUpdateJBN = false,
    this.chkDeleteJBN = false,
    this.chkEditChqDetails = false,
    this.chkStockReplace = false,
    this.chkStockeTransfer = false,
    this.chkAddBank = false,
    this.chkEditBank = false,
    this.chkDelBank = false,
    this.chkAddDeposit = false,
    this.chkAddWithdraw = false,
    this.chkWebAccess = false,
    this.chkUpdateItemPrice = false,
    this.chkDeleteItemPrice = false,
    this.chkDataSync = false,
    this.chkTOGReceived = false,
  });

  factory UserPermissions.fromJson(Map<String, dynamic> json) {
    bool _flag(dynamic value) {
      if (value == null) return false;
      if (value is bool) return value;
      if (value is int) return value == 1;
      if (value is String) return value.trim().isNotEmpty && value.trim() != "0";
      return false;
    }

    return UserPermissions(
      locaCode: json['locaCode']?.toString(),
      userCode: json['userCode']?.toString(),
      cName: json['cName']?.toString(),
      userName: json['userName']?.toString(),
      userType: json['userType']?.toString(),
      pinnumber: json['pinnumber']?.toString(),
      loginLocaCode: json['loginLocaCode']?.toString(),
      chkAddItem: _flag(json['chkAddItem']),
      chkEditItem: _flag(json['chkEditItem']),
      chkDelItem: _flag(json['chkDelItem']),
      chkAddCat: _flag(json['chkAddCat']),
      chkEditCat: _flag(json['chkEditCat']),
      chkDelCat: _flag(json['chkDelCat']),
      chkAddSup: _flag(json['chkAddSup']),
      chkEditSup: _flag(json['chkEditSup']),
      chkDelSup: _flag(json['chkDelSup']),
      chkAddCus: _flag(json['chkAddCus']),
      chkEditCus: _flag(json['chkEditCus']),
      chkDelCus: _flag(json['chkDelCus']),
      chkAddEmp: _flag(json['chkAddEmp']),
      chkEditEmp: _flag(json['chkEditEmp']),
      chkDelEmp: _flag(json['chkDelEmp']),
      chkAddUser: _flag(json['chkAddUser']),
      chkEditUser: _flag(json['chkEditUser']),
      chkDelUser: _flag(json['chkDelUser']),
      chkUserControl: _flag(json['chkUserControl']),
      chkUserControlOption: _flag(json['chkUserControlOption']),
      chkPurchase: _flag(json['chkPurchase']),
      chkPurchaseR: _flag(json['chkPurchaseR']),
      chkClearPurchase: _flag(json['chkClearPurchase']),
      chkCancelPurchase: _flag(json['chkCancelPurchase']),
      chkEditPurchase: _flag(json['chkEditPurchase']),
      chkHoldPurchase: _flag(json['chkHoldPurchase']),
      chkProceedGRN: _flag(json['chkProceedGRN']),
      chkInvoice: _flag(json['chkInvoice']),
      chkInvoiceR: _flag(json['chkInvoiceR']),
      chkPrintInvoice: _flag(json['chkPrintInvoice']),
      chkClearInvoice: _flag(json['chkClearInvoice']),
      chkCancelInvoice: _flag(json['chkCancelInvoice']),
      chkEditInvoice: _flag(json['chkEditInvoice']),
      chkHoldInvoice: _flag(json['chkHoldInvoice']),
      chkMakeQuotation: _flag(json['chkMakeQuotation']),
      chkMakeDNote: _flag(json['chkMakeDNote']),
      chkMakeReceipt: _flag(json['chkMakeReceipt']),
      chkDeleteHoldInv: _flag(json['chkDeleteHoldInv']),
      chkCreditSales: _flag(json['chkCreditSales']),
      chkPrintDaySummery: _flag(json['chkPrintDaySummery']),
      chkPayDue: _flag(json['chkPayDue']),
      chkQtyAdjust: _flag(json['chkQtyAdjust']),
      chkCashDenomination: _flag(json['chkCashDenomination']),
      chkCashDiscount: _flag(json['chkCashDiscount']),
      chkEmpDiscount: _flag(json['chkEmpDiscount']),
      chkUpdateCashDenomination: _flag(json['chkUpdateCashDenomination']),
      chkViewCashDenominationRpt: _flag(json['chkViewCashDenominationRpt']),
      chkPaidOut: _flag(json['chkPaidOut']),
      chkSalaryPayment: _flag(json['chkSalaryPayment']),
      chkAddIncome: _flag(json['chkAddIncome']),
      chkAddExpenses: _flag(json['chkAddExpenses']),
      chkProceedIncome: _flag(json['chkProceedIncome']),
      chkProceedExpenses: _flag(json['chkProceedExpenses']),
      chkAccountDet: _flag(json['chkAccountDet']),
      chkEmpRpt: _flag(json['chkEmpRpt']),
      chkCusRpt: _flag(json['chkCusRpt']),
      chkCatRpt: _flag(json['chkCatRpt']),
      chkSupRpt: _flag(json['chkSupRpt']),
      chkItemRpt: _flag(json['chkItemRpt']),
      chkStockRpt: _flag(json['chkStockRpt']),
      chkViewHome: _flag(json['chkViewHome']),
      chkPriceChange: _flag(json['chkPriceChange']),
      chkChangeDate: _flag(json['chkChangeDate']),
      chkAddDCat: _flag(json['chkAddDCat']),
      chkEditDCat: _flag(json['chkEditDCat']),
      chkVenUpdate: _flag(json['chkVenUpdate']),
      chkDescUpdate: _flag(json['chkDescUpdate']),
      chkAddVatDet: _flag(json['chkAddVatDet']),
      chkShowUPrice: _flag(json['chkShowUPrice']),
      chkShowCost: _flag(json['chkShowCost']),
      chkAddJBN: _flag(json['chkAddJBN']),
      chkUpdateJBN: _flag(json['chkUpdateJBN']),
      chkDeleteJBN: _flag(json['chkDeleteJBN']),
      chkEditChqDetails: _flag(json['chkEditChqDetails']),
      chkStockReplace: _flag(json['chkStockReplace']),
      chkStockeTransfer: _flag(json['chkStockeTransfer']),
      chkAddBank: _flag(json['chkAddBank']),
      chkEditBank: _flag(json['chkEditBank']),
      chkDelBank: _flag(json['chkDelBank']),
      chkAddDeposit: _flag(json['chkAddDeposit']),
      chkAddWithdraw: _flag(json['chkAddWithdraw']),
      chkWebAccess: _flag(json['chkWebAccess']),
      chkUpdateItemPrice: _flag(json['chkUpdateItemPrice']),
      chkDeleteItemPrice: _flag(json['chkDeleteItemPrice']),
      chkDataSync: _flag(json['chkDataSync']),
      chkTOGReceived: _flag(json['chkTOGReceived']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'locaCode': locaCode,
      'userCode': userCode,
      'cName': cName,
      'userName': userName,
      'userType': userType,
      'pinnumber': pinnumber,
      'loginLocaCode': loginLocaCode,
      'chkAddItem': chkAddItem,
      'chkEditItem': chkEditItem,
      'chkDelItem': chkDelItem,
      'chkAddCat': chkAddCat,
      'chkEditCat': chkEditCat,
      'chkDelCat': chkDelCat,
      'chkAddSup': chkAddSup,
      'chkEditSup': chkEditSup,
      'chkDelSup': chkDelSup,
      'chkAddCus': chkAddCus,
      'chkEditCus': chkEditCus,
      'chkDelCus': chkDelCus,
      'chkAddEmp': chkAddEmp,
      'chkEditEmp': chkEditEmp,
      'chkDelEmp': chkDelEmp,
      'chkAddUser': chkAddUser,
      'chkEditUser': chkEditUser,
      'chkDelUser': chkDelUser,
      'chkUserControl': chkUserControl,
      'chkUserControlOption': chkUserControlOption,
      'chkPurchase': chkPurchase,
      'chkPurchaseR': chkPurchaseR,
      'chkClearPurchase': chkClearPurchase,
      'chkCancelPurchase': chkCancelPurchase,
      'chkEditPurchase': chkEditPurchase,
      'chkHoldPurchase': chkHoldPurchase,
      'chkProceedGRN': chkProceedGRN,
      'chkInvoice': chkInvoice,
      'chkInvoiceR': chkInvoiceR,
      'chkPrintInvoice': chkPrintInvoice,
      'chkClearInvoice': chkClearInvoice,
      'chkCancelInvoice': chkCancelInvoice,
      'chkEditInvoice': chkEditInvoice,
      'chkHoldInvoice': chkHoldInvoice,
      'chkMakeQuotation': chkMakeQuotation,
      'chkMakeDNote': chkMakeDNote,
      'chkMakeReceipt': chkMakeReceipt,
      'chkDeleteHoldInv': chkDeleteHoldInv,
      'chkCreditSales': chkCreditSales,
      'chkPrintDaySummery': chkPrintDaySummery,
      'chkPayDue': chkPayDue,
      'chkQtyAdjust': chkQtyAdjust,
      'chkCashDenomination': chkCashDenomination,
      'chkCashDiscount': chkCashDiscount,
      'chkEmpDiscount': chkEmpDiscount,
      'chkUpdateCashDenomination': chkUpdateCashDenomination,
      'chkViewCashDenominationRpt': chkViewCashDenominationRpt,
      'chkPaidOut': chkPaidOut,
      'chkSalaryPayment': chkSalaryPayment,
      'chkAddIncome': chkAddIncome,
      'chkAddExpenses': chkAddExpenses,
      'chkProceedIncome': chkProceedIncome,
      'chkProceedExpenses': chkProceedExpenses,
      'chkAccountDet': chkAccountDet,
      'chkEmpRpt': chkEmpRpt,
      'chkCusRpt': chkCusRpt,
      'chkCatRpt': chkCatRpt,
      'chkSupRpt': chkSupRpt,
      'chkItemRpt': chkItemRpt,
      'chkStockRpt': chkStockRpt,
      'chkViewHome': chkViewHome,
      'chkPriceChange': chkPriceChange,
      'chkChangeDate': chkChangeDate,
      'chkAddDCat': chkAddDCat,
      'chkEditDCat': chkEditDCat,
      'chkVenUpdate': chkVenUpdate,
      'chkDescUpdate': chkDescUpdate,
      'chkAddVatDet': chkAddVatDet,
      'chkShowUPrice': chkShowUPrice,
      'chkShowCost': chkShowCost,
      'chkAddJBN': chkAddJBN,
      'chkUpdateJBN': chkUpdateJBN,
      'chkDeleteJBN': chkDeleteJBN,
      'chkEditChqDetails': chkEditChqDetails,
      'chkStockReplace': chkStockReplace,
      'chkStockeTransfer': chkStockeTransfer,
      'chkAddBank': chkAddBank,
      'chkEditBank': chkEditBank,
      'chkDelBank': chkDelBank,
      'chkAddDeposit': chkAddDeposit,
      'chkAddWithdraw': chkAddWithdraw,
      'chkWebAccess': chkWebAccess,
      'chkUpdateItemPrice': chkUpdateItemPrice,
      'chkDeleteItemPrice': chkDeleteItemPrice,
      'chkDataSync': chkDataSync,
      'chkTOGReceived': chkTOGReceived,
    };
  }
}