const fs = require('fs').promises;
const path = require('path');

async function generateProblemsJson() {
  console.log('문제 목록 생성 시작...');

  const javaDir = path.join(__dirname, '../cotest_java/src/main/java/com/codingtest');

  try {
    const files = await fs.readdir(javaDir);
    const javaFiles = files.filter(f => f.endsWith('.java') && !f.includes('Test'));

    const problems = [];

    for (const file of javaFiles) {
      const filePath = path.join(javaDir, file);
      const content = await fs.readFile(filePath, 'utf8');

      const problem = parseJavaFile(file, content);
      if (problem) {
        problems.push(problem);
      }
    }

    // 카테고리별, 레벨별 정렬
    problems.sort((a, b) => {
      if (a.category !== b.category) {
        return a.category.localeCompare(b.category);
      }
      return a.level - b.level;
    });

    // problems.json 저장
    const outputPath = path.join(__dirname, 'src/data/problems.json');
    await fs.writeFile(outputPath, JSON.stringify(problems, null, 2), 'utf8');

    console.log(`✅ ${problems.length}개 문제 정보 생성 완료`);
    console.log(`파일 위치: ${outputPath}`);

    // 카테고리별 통계
    const categories = {};
    problems.forEach(p => {
      categories[p.category] = (categories[p.category] || 0) + 1;
    });

    console.log('\n📊 카테고리별 문제 수:');
    Object.entries(categories).forEach(([category, count]) => {
      console.log(`  - ${category}: ${count}`);
    });

  } catch (error) {
    console.error('오류 발생:', error);
  }
}

function parseJavaFile(filename, content) {
  // 파일명에서 ID 추출
  const id = filename.replace('.java', '');

  // 카테고리 추출
  let category = 'Unknown';
  if (id.startsWith('BruteForce')) category = 'BruteForce';
  else if (id.startsWith('hash')) category = 'Hash';
  else if (id.startsWith('heap')) category = 'Heap';
  else if (id.startsWith('stack')) category = 'Stack';
  else if (id.startsWith('queue')) category = 'Queue';
  else if (id.startsWith('sort')) category = 'Sort';
  else if (id.startsWith('greedy')) category = 'Greedy';

  // 레벨 추출
  const levelMatch = id.match(/level(\d+)/);
  const level = levelMatch ? parseInt(levelMatch[1]) : 1;

  // 제목 추출: "1. 문제 (제목)" 패턴에서 괄호 안 제목 추출
  let title = id;
  const titleMatch = content.match(/1\.\s*문제\s*\((.+?)\)/);
  if (titleMatch) {
    title = titleMatch[1].trim();
  } else {
    // 괄호 없는 경우 "1. 문제" 다음 첫 텍스트 줄을 제목으로
    const descMatch = content.match(/1\.\s*문제\s*\n\s*\*\s*=+\s*\n\s*\*\s*(.+)/);
    if (descMatch) {
      // 첫 문장에서 핵심 키워드 추출 (40자 이내)
      let desc = descMatch[1].trim();
      if (desc.length > 40) desc = desc.substring(0, 40) + '...';
      title = desc;
    }
  }

  // 문제 설명 추출 (간단 버전)
  let shortDescription = '';
  const descMatch = content.match(/=+\s*\n\s*\*\s*1\.\s*문제\s*\n\s*\*\s*=+\s*\n\s*\*\s*(.+?)(?=\n\s*\*\s*\[|$)/s);
  if (descMatch) {
    shortDescription = descMatch[1]
      .split('\n')
      .map(line => line.trim().replace(/^\*\s*/, ''))
      .filter(line => line)
      .slice(0, 2)
      .join(' ')
      .trim();
  }

  return {
    id,
    category,
    level,
    title,
    shortDescription,
    filePath: `../cotest_java/src/main/java/com/codingtest/${filename}`
  };
}

// 스크립트 실행
generateProblemsJson();
