echo eSalesCommonClient
java -jar dist\LmigUtil.jar missingtests "D:\Workspaces\PLM\PmESalesCommonClient\src\main\webapp" "D:\Workspaces\PLM\PmESalesCommonClient\src\main\webapp\test\unit" "js" test\,.profile

echo eSalesDesktopCommonClient
java -jar dist\LmigUtil.jar missingtests "D:\Workspaces\PLM\PmESalesDesktopCommonClient\src\main\webapp" "D:\Workspaces\PLM\PmESalesDesktopCommonClient\src\main\webapp\test\unit" "js" test\,.profile

echo eSalesDesktopAutoClient
java -jar dist\LmigUtil.jar missingtests "D:\Workspaces\PLM\PmESalesDesktopAutoClient\src\main\webapp" "D:\Workspaces\PLM\PmESalesDesktopAutoClient\src\main\webapp\test\unit" "js" test\,.profile