


WITH following AS (
	SELECT follower_id AS id FROM follows
	WHERE followed_id = 994 AND status = 'active'
), followers AS (
	SELECT followed_id AS id FROM follows
	WHERE follower_id = 994 AND status = 'active'
)
SELECT u.username, u.id
FROM following fw
INNER JOIN followers fr
	ON fw.id = fr.id
INNER JOIN users u
	ON fr.id = u.id
	


SELECT hashtag_id, COUNT(post_id) FROM post_hashtag
GROUP BY hashtag_id

SELECT u.usern
FROM follows f
INNER JOIN users u ON f.followed_id = u.id
WHERE f.follower_id = 994