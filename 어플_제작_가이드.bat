@echo off
chcp 65001 > nul
title 파킹나우 스마트폰 어플 제작 및 설치 가이드
cls
echo ===================================================================
echo      [파킹나우] 스마트폰 어플(앱) 제작 및 설치 방법
echo ===================================================================
echo.
echo  [방법 1] 스마트폰 브라우저에서 '홈 화면에 추가' / '앱 설치' (가장 추천)
echo     - 주소: https://dd5650.github.io/parkingnow/
echo     - 갤럭시/아이폰 모두 1초 만에 네이티브 어플 아이콘으로 설치됩니다.
echo.
echo  [방법 2] PWABuilder를 통한 1분 안드로이드 APK 파일 생성
echo     - https://www.pwabuilder.com 접속 후 주소 입력 -> Download APK
echo.
echo  [방법 3] 안드로이드 스튜디오 (android_app 폴더 직접 빌드)
echo.
echo  [방법 4] GitHub Actions 클라우드 자동 빌드
echo     - '깃허브_업로드.bat' 실행 후 GitHub Actions 탭에서 APK 다운로드
echo.
echo ===================================================================
echo  상세 가이드 문서를 메모장으로 엽니다...
echo ===================================================================
start "" notepad.exe "APK_제작_가이드.md"
pause
