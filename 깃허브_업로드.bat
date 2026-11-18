@echo off
chcp 65001 > nul
title 깃허브 업로드 (GitHub Push)
cls

echo ===================================================================
echo     🚗 [파킹파인더] GitHub 원격 저장소 업로드 (Push)
echo ===================================================================
echo.
echo  원격 저장소: https://github.com/dd5650/parkingnow.git
echo.
echo  잠시 후 GitHub 로그인 창이나 브라우저 인증 화면이 뜹니다.
echo  브라우저에서 [Authorize git-ecosystem] 버튼을 눌러 승인해 주세요.
echo ===================================================================
echo.

"C:\Program Files\Git\cmd\git.exe" push -u origin main

echo.
if %errorlevel% equ 0 (
    echo ===================================================================
    echo  🎉 축하합니다! GitHub 업로드가 성공적으로 완료되었습니다!
    echo.
    echo  👉 저장소 확인: https://github.com/dd5650/parkingnow
    echo ===================================================================
) else (
    echo [!] 업로드 중 오류가 발생했습니다.
    echo     GitHub 로그인 또는 권한 설정을 확인해 주세요.
)
echo.
pause
