# 🚗 파킹파인더 (ParkingNow / ParkingFinder)

> **전국 18,173개소 공공데이터 기반 실시간 주차장 검색 및 턴바이턴 웹 내비게이션 서비스**  
> PC와 스마트폰(모바일) 어디서든 별도의 앱 설치 없이 웹 브라우저로 바로 사용할 수 있습니다.

![전국 주차장 안내](https://img.shields.io/badge/데이터셋-전국_18%2C173개소-emerald?style=for-the-badge)
![플랫폼 지원](https://img.shields.io/badge/플랫폼-모바일%20%7C%20PC%20%7C%20PWA-blue?style=for-the-badge)
![오픈소스 지도](https://img.shields.io/badge/지도-Leaflet%20%7C%20VWorld%20%7C%20OSM-orange?style=for-the-badge)

---

## ✨ 핵심 기능

### 1. 전국 18,173개 주차장 완벽 시각화
- **공공데이터 표준 주차장 데이터셋** 내장 (12,611개 무료, 4,732개 공영, 830개 민영/유료).
- **Leaflet MarkerCluster** 기술 적용으로 18,000개 이상의 마커를 60 FPS로 초고속 렌더링.
- 지도 위에 `[🟢 무료 (0원)]`, `[🔵 1시간 1,200원]`, `[1시간 3,000원]` 가격 뱃지가 즉시 표시됩니다.

### 2. 턴바이턴 웹 자체 실시간 내비게이션
- 외부 앱 없이 웹 브라우저 안에서 **실제 도로 기준 경로 안내(OSRM)** 제공.
- **실시간 GPS 주행 모드:** 스마트폰 거치대에 두고 운전 시 내 차량 위치를 실시간 추적.
- **모의 주행 모드:** 출발 전 경로와 소요 시간을 미리 주행 애니메이션으로 확인.
- **한국어 음성 안내 (TTS):** 턴 지점 및 회전 안내 음성 자동 재생.
- **화면 꺼짐 방지:** 주행 중 스마트폰 화면이 꺼지지 않도록 Screen WakeLock 지원.

### 3. 스마트폰 & 모바일 최적화 (PWA)
- 스마트폰 전용 3단계 드래그 **바텀시트 (Bottom Sheet 서랍)** UI.
- 브라우저 메뉴에서 **[홈 화면에 추가]** 시 주소창 없는 전체 화면 네이티브 앱으로 구동.
- 카카오내비, 티맵(TMAP), 네이버 지도 원터치 딥링크 연동 지원.

---

## 🚀 빠른 시작 방법

### 1) PC에서 바로 실행하기
1. 저장소를 클론(Clone)하거나 다운로드합니다.
2. `index.html` 파일을 더블 클릭하여 크롬/엣지 등의 웹 브라우저로 엽니다.

### 2) 스마트폰에서 실행하기
1. 같은 Wi-Fi에 연결된 상태에서 `스마트폰_실행.bat`을 더블 클릭합니다.
2. 화면에 뜨는 QR 코드를 스마트폰 카메라로 비추면 바로 실행됩니다.

### 3) GitHub Pages로 무료 배포하기
1. 본 저장소의 **Settings > Pages** 메뉴로 이동합니다.
2. **Branch**를 `main` (또는 `master`), 폴더를 `/(root)`로 선택 후 Save합니다.
3. 몇 초 후 제공되는 GitHub Pages URL (`https://dd5650.github.io/parkingnow/`)로 전 세계 어디서든 모바일로 접속할 수 있습니다!

---

## 🛠 기술 스택
- **프론트엔드:** HTML5, CSS3, JavaScript (Vanilla ES6+), Tailwind CSS
- **지도 엔진:** Leaflet.js, Leaflet.markercluster, VWorld (국토교통부 고해상도 타일)
- **경로 및 내비:** OSRM (Open Source Routing Machine), Web Speech API
- **모바일 규격:** PWA (Progressive Web Apps), Screen WakeLock API, Geolocation API
