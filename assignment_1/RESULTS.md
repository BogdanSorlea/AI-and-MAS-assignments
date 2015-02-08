Level      Client    Time       Memory Used    Solution Length    Nodes Explored
SAD1       BFS       0.05s      9.10MB         19                 78    
SAD1.1     BFS       1.15s      145.46MB       19                 4550
SAD1.2     BFS       43.62s     2517.40MB      19                 70042
SAD2       BFS       130.49s    2588.50MB      N/A                61600
custom     BFS       149.48s    2588.91MB      N/A                59200
SAD1       DFS       0.03s      6.50MB         27                 44
SAD1.1     DFS       0.05s      7.15MB         37                 50
SAD1.2     DFS       0.08s      18.20MB        101                154
SAD2       DFS       4.76s      578.81MB       5781               6799
custom     DFS       0.06s      16.25MB        101                127

after memory optimization

SAD1       BFS       0.03s      2.60MB         19                 78
SAD1.1     BFS       0.34s      20.20MB        19                 4550
SAD1.2     BFS       21.63s     530.49MB       19                 70042
SAD2       BFS       >300s      835.83MB       N/A                147000
custom     BFS       >300s      938.37MB       N/A                132000
SAD1       DFS       0.03s      2.60MB         27                 44
SAD1.1     DFS       0.02s      2.60MB         37                 50
SAD1.2     DFS       0.05s      3.90MB         101                154
SAD2       DFS       2.77s      130.22MB       5781               6799
custom     DFS       0.06s      3.90MB         101                127
