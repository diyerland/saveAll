; 9 probs, max 2 goals, max 3 objs


(setq *TEST-PROBS*
    '((BB1-1 (holding C)
     ((arm-empty)
      (clear B)
      (object A)
      (object C)
      (object B)
      (on-table A)
      (on B C)
      (on C A)))
 (BB1-2 (clear B)
     ((holding A)
      (clear C)
      (on C B)
      (object A)
      (object C)
      (object B)
      (on-table B)))
(BB1-3 (and (on A C) (on-table B))
            ((object B)
             (on-table B)
             (object A)
             (on A B)
             (clear A)
             (object C)
             (clear C)
             (on-table C)
             (arm-empty)))
     (BB1-4 (and (clear B) (holding C))
            ((object B)
             (clear B)
             (on-table B)
             (object C)
             (clear C)
             (on-table C)
             (object A)
             (holding A)))
     (BB1-5 (and (on-table A) (on C B))
            ((object B)
             (on-table B)
             (object A)
             (on A B)
             (clear A)
             (object C)
             (clear C)
             (on-table C)
             (arm-empty)))
     (BB1-6 (and (on B A) (on C B))
            ((object B)
             (on-table B)
             (object A)
             (on A B)
             (clear A)
             (object C)
             (clear C)
             (on-table C)
             (arm-empty)))
     (BB1-7 (and (on C A) (clear B))
            ((object A)
             (clear A)
             (on-table A)
             (object B)
             (clear B)
             (on-table B)
             (object C)
             (clear C)
             (on-table C)
             (arm-empty)))
     (BB1-8 (and (on B C) (arm-empty))
           ((object A)
            (clear A)
            (on-table A)
            (object B)
            (clear B)
            (on-table B)
            (object C)
            (clear C)
            (on-table C)
            (arm-empty)))
     (BB1-9 (on C A)
           ((object C)
            (on-table C)
            (object B)
            (on B C)
            (object A)
            (on A B)
            (clear A)
            (arm-empty)))))