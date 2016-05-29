; 9 simple test probs, 1 goal each

  (setq *TEST-PROBS*
   '((BB0-1 (on A B)
      ((clear E)
      (clear D)
      (clear B)
      (clear C)
      (clear A)
      (object E)
      (object D)
      (object A)
      (object B)
      (object C)
      (on-table E)
      (on-table D)
      (on-table B)
      (on-table C)
      (on-table A)
      (arm-empty)))
     (BB0-2 (on-table A)
           ((object C)
	     (object D)
             (on-table D)
             (clear D)
             (on-table C)
             (object A)
             (on A C)
             (clear A)
             (object B)
             (clear B)
             (on-table B)
             (arm-empty)))
     (BB0-3 (on C B)
            ((object D)
	     (object E)
             (on-tableE)
             (clear E)
             (on-table D)
             (object A)
             (on A D)
             (clear A)
             (object B)
             (clear B)
             (on-table B)
             (object C)
             (clear C)
             (on-table C)
             (arm-empty)))
     (BB0-4 (on-table A)
            ((object B)
             (on-table B)
	     (object D)
             (on-table D)
             (clear D)
             (object A)
             (on A B)
             (clear A)
             (arm-empty)))
     (BB0-5 (clear A)
            ((object B) 
	     (clear B) 
	     (on-table B)
 	     (object D)
             (on-table D)
             (clear D)
	     (object A) (holding A)))
     (BB0-6 (on-table A)
            ((object C)
             (on-table C)
	     (object D)
             (on-table D)
             (clear D)
             (object B)
             (on B C)
             (object A)
             (on A B)
             (clear A)
             (arm-empty)))
     (BB0-7 (on-table B)
            ((object A)
             (clear A)
             (on-table A)
             (object D)
	     (object D)
             (on-table D)
             (clear D)
             (on-table D)
             (object C)
             (on C D)
             (object B)
             (on B C)
             (clear B)
             (arm-empty)))
     (BB0-8 (arm-empty)
            ((object C)
             (on-table C)
	     (clear C)
             (object A)
             (holding A)
             (object B)
             (clear B)
             (on-table B)))
     (BB0-9 (arm-empty)
            ((object C)
             (on-table C)
	     (on B C)
             (object A)
             (holding A)
             (object B)
             (clear B)))
     (BB0-10 (on-table A)
            ((object D)
             (on-table D)
             (object C)
             (on C D)
             (object B)
             (on B C)
             (object A)
             (on A B)
             (clear A)
             (arm-empty)))))

(setq *AUX-COMMANDS*
  '((BB0-1 (expand-all))
    (BB0-2 (expand-all))
    (BB0-3 (expand-all))
    (BB0-4 (expand-all))
    (BB0-5 (expand-all))
    (BB0-6 (expand-all))
    (BB0-7 (expand-all))
    (BB0-8 (expand-all))
    (BB0-9 (expand-all))
    (BB0-10 (expand-all))))












