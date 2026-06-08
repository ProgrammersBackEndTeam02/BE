-- products
INSERT IGNORE INTO products (id, product_name, is_decaf, roasting_level, acidity, product_price, stock, description, thumbnail_image_url, detail_page_image_url) VALUES
(1,  '에티오피아 예가체프',       false, 'LIGHT',  true,  18000, 50,  '플로럴하고 과일향이 풍부한 에티오피아 원두',         '/images/thumb/yirgacheffe.jpg',   '/images/detail/yirgacheffe.jpg'),
(2,  '콜롬비아 수프리모',         false, 'MEDIUM', false, 16000, 80,  '부드러운 바디감과 달콤한 캐러멜 향의 콜롬비아 원두', '/images/thumb/supremo.jpg',       '/images/detail/supremo.jpg'),
(3,  '브라질 산토스',             false, 'DARK',   false, 14000, 100, '고소한 견과류 향과 진한 바디감의 브라질 원두',       '/images/thumb/santos.jpg',        '/images/detail/santos.jpg'),
(4,  '케냐 AA',                   false, 'LIGHT',  true,  20000, 40,  '블랙커런트와 토마토 향이 특징인 케냐 최고급 원두',   '/images/thumb/kenya_aa.jpg',      '/images/detail/kenya_aa.jpg');

-- orders
INSERT IGNORE INTO orders (id, customer_email, address, zip_code, order_status, total_price, created_at, updated_at) VALUES
(1,  'kim@test.com',   '서울 강남구 테헤란로 123',   '06234', 'PROCESSING', 18000,  NOW(), NOW()),
(2,  'lee@test.com',   '서울 마포구 홍익로 45',       '04066', 'SHIPPING',   32000,  NOW(), NOW()),
(3,  'park@test.com',  '부산 해운대구 해운대로 77',   '48094', 'DELIVERED',  20000,  NOW(), NOW()),
(4,  'choi@test.com',  '대구 중구 동성로 11',         '41929', 'CANCELLED',  14000,  NOW(), NOW()),
(5,  'jung@test.com',  '인천 남동구 구월로 200',      '21565', 'PROCESSING', 45000,  NOW(), NOW()),
(6,  'han@test.com',   '광주 서구 상무대로 55',       '61949', 'SHIPPING',   50000,  NOW(), NOW()),
(7,  'yoon@test.com',  '대전 유성구 대학로 99',       '34134', 'DELIVERED',  35000,  NOW(), NOW()),
(8,  'lim@test.com',   '울산 남구 삼산로 88',         '44699', 'PROCESSING', 36000,  NOW(), NOW()),
(9,  'shin@test.com',  '경기 수원시 팔달구 정조로 1', '16359', 'SHIPPING',   22000,  NOW(), NOW()),
(10, 'oh@test.com',    '제주 제주시 연동로 33',       '63166', 'DELIVERED',  65000,  NOW(), NOW());

-- order_items
INSERT IGNORE INTO order_items (id, order_id, product_id, quantity, price_at_order, total_price) VALUES
(1,  1,  1,  1, 18000, 18000),
(2,  2,  2,  1, 16000, 16000),
(3,  2,  3,  1, 14000, 14000),
(4,  3,  4,  1, 20000, 20000),
(5,  4,  3,  1, 14000, 14000),
(6,  5,  8,  1, 45000, 45000),
(7,  6,  9,  1, 50000, 50000),
(8,  7,  10, 1, 35000, 35000),
(9,  8,  2,  1, 16000, 16000),
(10, 8,  5,  1, 17000, 17000),
(11, 9,  6,  1, 22000, 22000),
(12, 10, 8,  1, 45000, 45000),
(13, 10, 9,  1, 50000, 50000);
