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

heuristic 1 (max of manhattan distance from agent to box - 1 + manhattan distance from box to goal)

SAD1       A*        0.05s      3.25MB         19                 77
SAD1.1     A*        0.34s      28.60MB        19                 2055
SAD1.2     A*        1.22s      64.49MB        19                 7129
SAD2       A*        6.76s      70.18MB        19                 14932
custom     A*        8.07s      342.54MB       19                 19981
Firefly    A*        34.17s     274.46MB       60                 92054
Crunch     A*        >300s      343.24MB       N/A                297600

SAD1       WA*       0.03s      2.60MB         23                 25
SAD1.1     WA*       0.01s      2.60MB         23                 25
SAD1.2     WA*       0.03s      2.60MB         23                 25
SAD2       WA*       0.03s      2.60MB         23                 25
custom     WA*       0.03s      2.60MB         23                 25
Firefly    WA*       72.95s     254.43MB       116                110988
Crunch     WA*       >300s      924.80MB       N/A                458600

SAD1       GRD       0.02s      2.60MB         23                 25
SAD1.1     GRD       0.02s      2.60MB         23                 25
SAD1.2     GRD       0.03s      2.60MB         23                 25
SAD2       GRD       0.03s      2.60MB         23                 25
custom     GRD       0.03s      2.60MB         23                 25
Firefly    GRD       52.01s     911.04MB       154                148127
Crunch     GRD       >300s      936.36MB       N/A                671000

heuristic 2 (sum of (manhattan distances - 1) for each box-goal pair)

SAD1       A*        0.05s      3.25MB         19                 77
SAD1.1     A*        0.33s      28.60MB        19                 2055
SAD1.2     A*        1.19s      64.51MB        19                 7129
SAD2       A*        4.48s      65.22MB        19                 14932
custom     A*        8.01s      409.04MB       19                 19981
Firefly    A*        6.86s      152.78MB       78                 35244
Crunch     A*        >300s      827.14MB       N/A                446200

SAD1       WA*       0.03s      2.60MB         23                 25
SAD1.1     WA*       0.03s      2.60MB         23                 25
SAD1.2     WA*       0.03s      2.60MB         23                 25
SAD2       WA*       0.03s      2.60MB         23                 25
custom     WA*       0.03s      2.60MB         23                 25
Firefly    WA*       0.61s      26.03MB        106                4249
Crunch     WA*       >300s      769.64MB       N/A                332600

SAD1       GRD       0.03s      2.60MB         23                 25
SAD1.1     GRD       0.03s      2.60MB         23                 25
SAD1.2     GRD       0.03s      2.60MB         23                 25
SAD2       GRD       0.03s      2.60MB         23                 25
custom     GRD       0.03s      2.60MB         23                 25
Firefly    GRD       0.56s      23.18MB        104                4279
Crunch     GRD       >300s      735.36MB       N/A                571800
