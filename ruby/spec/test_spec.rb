describe Prueba do
  let(:prueba) { Prueba.new }

  describe '#materia' do
    it 'debería pasar este test' do
      expect(prueba.materia).to be :tadp
    end
  end

  describe '#test' do
    it 'debería pasar este test también' do
      expect(2 + 2).to eq 4
    end
  end
end