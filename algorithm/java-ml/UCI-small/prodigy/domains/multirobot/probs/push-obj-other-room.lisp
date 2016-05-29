;;;the robot has just to push a box that is near the door to another room
(load-goal '(in-room box1 rram))

(load-start-state
 '((is-type rpdp room)
   (is-type rclk room)
   (is-type rram room)
   (is-type drclkrpdp door)
   (is-type drramrclk door)
   (is-type box1 box)
   (is-type robot1 robot)
   (is-type robot2 robot)
   (connects drramrclk rclk rram)
   (connects drramrclk rram rclk)
   (connects drclkrpdp rclk rpdp)
   (connects drclkrpdp rpdp rclk)
   (pushable box1)
   (weight box1 50)
   (next-to box1 drclkrpdp)
   (statis drramrclk open)
   (statis drclkrpdp open)
;   (statis drclkrpdp locked)
   (in-room box1 rpdp)
   (in-room robot1 rpdp)
   (in-room robot2 rram)
;  (in-room keys rram)
;  (is-key drclkrpdp keys)
;  (is-type keys key)
   ))
