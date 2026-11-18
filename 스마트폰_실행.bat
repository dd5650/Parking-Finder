@echo off
chcp 65001 > nul
title 파킹파인더 모바일 접속 서버
cls

echo ===================================================================
echo     🚗 [파킹파인더] 스마트폰 실시간 접속 로컬 서버
echo ===================================================================
echo.

:: 파이썬으로 현재 PC의 Wi-Fi 로컬 IP 자동 감지
for /f "delims=" %%i in ('python -c "import socket; s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM); s.connect(('8.8.8.8', 80)); print(s.getsockname()[0]); s.close()" 2^>nul') do set LOCAL_IP=%%i

if "%LOCAL_IP%"=="" set LOCAL_IP=127.0.0.1

echo  [!] 스마트폰과 PC가 같은 Wi-Fi(공유기)에 연결되어 있어야 합니다.
echo.
echo  📱 스마트폰 카메라 또는 모바일 브라우저(Safari, Chrome) 주소창에
echo     아래 주소를 입력하여 접속하세요:
echo.
echo     👉  http://%LOCAL_IP%:8000
echo.
echo  💡 팁: 접속 후 모바일 브라우저 메뉴에서 [홈 화면에 추가]를 누르면
echo        스마트폰 앱처럼 전체 화면으로 사용할 수 있습니다!
echo.
echo  [ 서버 종료 방법: 이 창을 닫거나 Ctrl + C 를 누르세요 ]
echo ===================================================================
echo.

:: PC 브라우저 자동 오픈
start http://localhost:8000

:: 파이썬 웹서버 가동
python -m http.server 8000
