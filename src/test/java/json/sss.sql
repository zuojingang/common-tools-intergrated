SELECT work_order_id,
       order_id,
       plan_start_time,
       practical_start_time,
       practical_finish_time,
       plan_finish_time,
       issue_time,
       abandoned_time,
       device_id,
       work_order_no,
       ic_card,
       the_sample_color,
       weight_num,
       craft_sum_time,
       length_num,
       issue_status,
       bussines_status,
       deleted,
       org_code,
       create_user_id,
       create_time,
       update_user_id,
       update_time,
       sort_num,
       the_dyeing_machine_time
FROM production_work_order
WHERE 1 = 1
  and ((plan_finish_time  <![CDATA[<=]]> 33195958635 or practical_finish_time <![CDATA[ <=]]> 33195958635)
    or (practical_start_time  <![CDATA[>=]]> 33195958635 and practical_finish_time is null))
  and device_id = '1438815381171195906'
          and issue_status in (0, 30)
          and craft_sum_time >= 0
ORDER BY sort_num DESC limit 1



    {"startTime":"2021-12-09 04:00:00","endTime":"2021-12-12 18:00:00","deviceIds":["1438815381171195906","1438815381171195906"]}