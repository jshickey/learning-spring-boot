class HelloSpock extends Specification {
	@Unroll
	def "Test length of name #name should be #length"() {
		expect: "Length of name to be #length"
		name.size() == length

		where: "The person's name is #name"
		name     | length
		"Spock"  | 50
		"Kirk"   | 4
		"Scotty" | 6
	}
}