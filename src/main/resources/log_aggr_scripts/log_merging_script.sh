#!/bin/bash

# 작업 개요 : 시간대별 로그에서, 타입별 로그를 추출하고, 분석계 전달을 위해 날짜별 단위로 통합

# 1. 작업 경로 설정
bk_dir="{로그 백업본(직접 작업X) 보관 경로}"
work_dir="{로그 취합 작업 수행을 위한 2차백업 및 편집 경로}"
mkdir $work_dir

# 2. 작업 대상 날짜 설정 (리스트로 여러 날짜 지정 후 순회 필요)
work_target_ymd="{YYYYMMDD}"

# 3. 작업 대상 로그 복사 및 압축 해제
cp "$bk_dir"/log_"$work_target_ymd"_* "$work_dir"/"$work_target_ymd"/

# 4. 로그타입 필터 후 필터링 이전 파일 삭제
for logfile in "$work_dir"/log*; do
        filename=$(basename "$logfile")
        zcat "$logfile" | grep ^d > "$work_dir"/"$filename"
        echo "$logfile" filtering done
        rm "$logfile"
done

# 5. 필터링된 로그 하나의 파일로 병합
file_full="$work_dir"/"$work_target_ymd"_merged.log
echo "$file_full"
touch "$file_full"
ls -al "$file_full"

for logfile in "$work_dir"/log_adv*; do
        cat "$logfile" >> "$file_full"
        ls -al "$file_full"
        rm "$logfile"
done

# 6. 작업 완료된 내역에 대해서 로그 라인 수 등을 이메일 전송
log_count=$(wc -l "${work_target_ymd}_merged.log")
# #### 이메일 전송

# 7. 통합 완료된 로그를 1차 백업경로에 적재 (인스턴스 디스크 부담 줄임)
# #### 1) S3 에 로그 적재
aws s3 cp FILE_PATH s3://BUCKET_NAME/
# #### 2) RDB에 날짜별, 로그 타입별 단순 통계 적재하는 쿼리를 여기에 추가 (DW)
# ####    (For 문으로 아래를 감싸서 여러 행 인서트)
echo "insert into LOG_AGGR_TBL_NAME (col1, ..., colN) values (val1, ..., valN);" | mysql -u USER_NAME -h DB_HOST -pDB_PASS DB_NAME
# #### 3) 추가로 분석계 Data pipeline 구현할 부분이 있다면 여기에서 작업 시작
